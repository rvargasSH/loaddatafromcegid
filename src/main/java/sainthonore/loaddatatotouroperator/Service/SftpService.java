package sainthonore.loaddatatotouroperator.Service;

import java.io.BufferedInputStream;
import sainthonore.loaddatatotouroperator.Util.FTPErrors;

import java.io.File;

public interface SftpService {

    void connectToFTP() throws FTPErrors;

    String[] getFilesNamesFromSftp(String directoryPath) throws FTPErrors;

    void downloadFileFromFTP(String ftpRelativePath, String copytoPath) throws FTPErrors;

    BufferedInputStream readFileFromFtp(String path) throws FTPErrors;

    void disconnectFTP() throws FTPErrors;
}
