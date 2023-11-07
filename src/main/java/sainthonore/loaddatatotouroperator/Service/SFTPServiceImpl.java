package sainthonore.loaddatatotouroperator.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sainthonore.loaddatatotouroperator.Util.ErrorMessage;
import sainthonore.loaddatatotouroperator.Util.FTPErrors;

import org.slf4j.Logger;

@Service
public class SFTPServiceImpl implements SftpService {
    /**
     * FTP connection handler
     */
    FTPClient ftpconnection;
    private Logger logger = LoggerFactory.getLogger(SFTPServiceImpl.class);

    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.user}")
    private String sftpUser;

    @Value("${sftp.password}")
    private String sftpPass;

    @Value("${sftp.path}")
    private String sftpPath;

    /**
     * Method that implement FTP connection.
     * 
     * @param host IP of FTP server
     * @param user FTP valid user
     * @param pass FTP valid pass for user
     * @throws FTPErrors Set of possible errors associated with connection process.
     */
    @Override
    public void connectToFTP() throws FTPErrors {
        ftpconnection = new FTPClient();
        ftpconnection.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        try {
            ftpconnection.connect(sftpHost);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-1,
                    "No fue posible conectarse al FTP a través del host=" + sftpHost);
            logger.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
        reply = ftpconnection.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            try {
                ftpconnection.disconnect();
            } catch (IOException e) {
                ErrorMessage errorMessage = new ErrorMessage(-2,
                        "No fue posible conectarse al FTP, el host=" + sftpHost + " entregó la respuesta=" + reply);
                logger.error(errorMessage.toString());
                throw new FTPErrors(errorMessage);
            }
        }
        try {
            ftpconnection.login(sftpUser, sftpPass);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-3,
                    "El usuario=" + sftpUser + ", y el pass=**** no fueron válidos para la autenticación.");
            logger.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
        try {
            ftpconnection.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-4, "El tipo de dato para la transferencia no es válido.");
            logger.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
        ftpconnection.enterLocalPassiveMode();
    }

    /**
     * Method for download files from FTP.
     * 
     * @param ftpRelativePath Relative path of file to download into FTP server.
     * @param copytoPath      Path to copy the file in download process.
     * @throws FTPErrors Set of errors associated with download process.
     */
    @Override
    public void downloadFileFromFTP(String ftpRelativePath, String copytoPath) throws FTPErrors {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(copytoPath);
        } catch (FileNotFoundException e) {
            ErrorMessage errorMessage = new ErrorMessage(-6,
                    "No se pudo obtener la referencia a la carpeta relativa donde guardar, verifique la ruta y los permisos.");
            logger.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
        try {
            this.ftpconnection.retrieveFile(ftpRelativePath, fos);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-7, "No se pudo descargar el archivo.");
            logger.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
    }

    public BufferedInputStream readFileFromFtp(String Filepath) throws FTPErrors {
        System.out.println(Filepath);
        try {
            InputStream file = this.ftpconnection.retrieveFileStream(Filepath);
            BufferedInputStream infoFile = new BufferedInputStream(file);
            return infoFile;
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-7, "No se pudo descargar el archivo.");
            logger.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }

    }

    public String[] getFilesNamesFromSftp(String directoryPath) throws FTPErrors {

        try {
            return this.ftpconnection.listNames(directoryPath);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method for release the FTP connection.
     * 
     * @throws FTPErrors Error if unplugged process failed.
     */
    @Override
    public void disconnectFTP() throws FTPErrors {
        if (this.ftpconnection.isConnected()) {
            try {
                this.ftpconnection.logout();
                this.ftpconnection.disconnect();
            } catch (IOException f) {
                throw new FTPErrors(
                        new ErrorMessage(-8, "Ha ocurrido un error al realizar la desconexión del servidor FTP"));
            }
        }
    }
}
