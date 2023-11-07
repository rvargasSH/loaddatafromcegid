package sainthonore.loaddatatotouroperator.Controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import sainthonore.loaddatatotouroperator.Model.ProductMaster;
import sainthonore.loaddatatotouroperator.Model.Store;
import sainthonore.loaddatatotouroperator.Model.StoreInventory;
import sainthonore.loaddatatotouroperator.Service.ProductMasterService;
import sainthonore.loaddatatotouroperator.Service.SftpService;
import sainthonore.loaddatatotouroperator.Service.StoreInventoryService;
import sainthonore.loaddatatotouroperator.Util.FTPErrors;
import sainthonore.loaddatatotouroperator.Repository.StoreRepository;

@RestController
@RequestMapping("/api/toload-sftp-data/products")
@EnableScheduling
public class ReadProductsController {

    @Autowired
    private SftpService ftpService;

    @Autowired
    private ProductMasterService productMasterService;

    @Autowired
    private StoreInventoryService storeInventoryService;

    @Autowired
    private StoreRepository storeRepository;

    @Scheduled(cron = "00 00 03 * * *")
    @RequestMapping(value = "load-categories-and-products", method = RequestMethod.GET)
    public ResponseEntity<?> loadCategoriesAndProducts() throws FTPErrors, IOException {
        Date date = new Date();
        String direcotyPath = "/Reportes/";

        ftpService.connectToFTP();
        String[] files = ftpService.getFilesNamesFromSftp(direcotyPath);
        String nameLastFile = "";
        for (String string : files) {
            nameLastFile = string;
        }
        if (nameLastFile.substring(0, 21).equals("Articulos_Sales_Force")) {
            BufferedInputStream infoFile = ftpService.readFileFromFtp(direcotyPath +
                    nameLastFile);
            int bytesRead;
            byte[] buffer = new byte[1024];
            String fileContent = null;
            Integer counter = 1;
            while ((bytesRead = infoFile.read(buffer)) != -1) {
                fileContent = new String(buffer, 0, bytesRead);
                String[] campos = fileContent.split("[\r\n]+");
                for (String line : campos) {
                    ProductMaster productMaster = new ProductMaster();
                    String[] columns = line.split(";");
                    if (columns.length >= 16 && counter > 1) {
                        Optional<ProductMaster> validateProduct = productMasterService
                                .findByMaterialCode(columns[0].replace("\u0000", ""));
                        if (validateProduct.isPresent()) {
                            productMaster = validateProduct.get();
                        }
                        productMaster.setCreatedAt(date);
                        productMaster.setMaterialCode(columns[0].replace("\u0000", ""));
                        productMaster.setMaterialName(columns[2].replace("\u0000", ""));
                        productMaster.setMaterialBrand(columns[4].replace("\u0000", ""));
                        productMaster.setMaterialFamily(columns[5].replace("\u0000", ""));
                        productMaster.setMaterialCategory(columns[7].replace("\u0000", ""));
                        productMaster.setMaterialSubcategory(columns[9].replace("\u0000", ""));
                        productMasterService.saveEntity(productMaster);
                    }
                    counter++;
                }

            }
        }
        ftpService.disconnectFTP();

        loadInventoryFileByStore();

        return ResponseEntity.ok(null);
    }

    void loadInventoryFileByStore() throws FTPErrors, IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<Store> stores = storeRepository.findByStoStatus(true);
        for (Store store : stores) {
            ftpService.connectToFTP();
            BufferedInputStream infoFile = ftpService
                    .readFileFromFtp("/Reportes Inventarios/" + store.getStoCegidId() + "_"
                            + dateFormat.format(new Date()) + ".csv");
            int bytesRead;
            byte[] buffer = new byte[1024];
            String fileContent = null;
            Integer counter = 1;
            storeInventoryService.deleteAll();
            while ((bytesRead = infoFile.read(buffer)) != -1) {
                fileContent = new String(buffer, 0, bytesRead);
                String[] campos = fileContent.split("[\r\n]+");
                for (String line : campos) {

                    String[] columns = line.split(";");
                    if (columns.length >= 4 && counter > 1) {
                        try {
                            StoreInventory storeInventory = new StoreInventory();
                            storeInventory.setCreatedAt(new Date());
                            storeInventory.setMaterialCode(columns[0].replace("\u0000", ""));
                            storeInventory.setMaterialStock(Integer.parseInt(columns[2].replace("\u0000", "")));
                            storeInventory.setStoId(store.getStoId());

                            storeInventoryService.saveEntity(storeInventory);
                        } catch (Exception e) {
                            System.out.println("No se pudo insertar el producto" + columns[0].replace("\u0000", ""));
                        }

                    }
                    counter++;
                }
            }
            productMasterService.validateCategoriesAndProducts(Integer.parseInt(store.getStoId().toString()));

        }
    }

}
