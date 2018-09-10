package info.doetsch.filetransfer.helpers;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Class for performing file transfers / data transfers to destination files
 *
 * @author bdoetsch
 */
public class FileTransferHelper implements AutoCloseable {

    private String targetHost;
    private ChannelSftp sftp;
    private Session session;
    private Logger logger;
    private boolean open;

    @SuppressWarnings("unused")
    private FileTransferHelper() {
    }

    /**
     * Constructor.
     *
     * @param username     the username of the connection credentials
     * @param secret       the secret of the connection credentials
     * @param targetHost   the host to connect to
     * @param port         the port on the host
     * @param checkHostKey activate/deactivate the host key checking
     * @param privateKey   if secret is a private key
     * @throws JSchException
     */
    public FileTransferHelper(String username, String secret, String targetHost, int port, boolean checkHostKey, boolean privateKey)
            throws JSchException {
        this.logger = LoggerFactory.getLogger(getClass().getSimpleName());
        this.targetHost = targetHost;
        JSch jsch = new JSch();
        this.session = jsch.getSession(username, targetHost, port);
        if (privateKey) {
            jsch.addIdentity(secret);
        } else {
            this.session.setPassword(secret);
        }
        this.session.setTimeout(60000);
        this.session.setConfig("StrictHostKeyChecking", checkHostKey ? "yes" : "no");
    }


    /**
     * Performs a file transfer via sftp
     *
     * @param targetDirectory the target directory
     * @param fileName        the target filename
     * @param payload         the data to be sent
     * @throws JSchException
     * @throws SftpException
     */
    public void sftp(String targetDirectory, String fileName, InputStream payload) throws JSchException, SftpException {
        if (open) {
            this.sftp.cd(targetDirectory);
            this.sftp.put(payload, fileName + ".tmp");
            this.sftp.rename(fileName + ".tmp", fileName);
            logger.info("Put file " + fileName + " via SFTP to " + targetHost);
        } else {
            throw new SftpException(0, "Connection is not open!");
        }
    }

    /**
     * Performs a file transfer via sftp - convenience method
     *
     * @param targetDirectory the target directory
     * @param fileName        the target filename
     * @param payload         the payload as String
     * @param encoding        the encoding, e.g. UTF-8
     * @throws JSchException
     * @throws SftpException
     * @throws UnsupportedEncodingException
     */
    public void sftp(String targetDirectory, String fileName, String payload, String encoding)
            throws UnsupportedEncodingException, JSchException, SftpException {
        this.sftp(targetDirectory, fileName, new ByteArrayInputStream(payload.getBytes(encoding)));
    }

    /**
     * open the connection
     *
     * @return boolean
     */
    public boolean openConnection() {
        this.logger.info("Open connection to " + targetHost);
        try {
            this.session.connect();
            this.logger.info("Connected to " + targetHost);
            this.logger.info("Open channel sftp to " + targetHost);
            this.sftp = (ChannelSftp) session.openChannel("sftp");
            this.sftp.connect();
            this.logger.info("Connection open");
            this.setOpen(true);
            return true;
        } catch (JSchException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    private void setOpen(boolean b) {
        this.open = b;
    }

    /**
     * close the connection
     */
    @Override
    public void close() {
        if (this.session != null) {
            this.session.disconnect();
            if (this.sftp != null) {
                this.sftp.disconnect();
            }
            open = false;
            logger.info("Disconnected from host " + targetHost);
        }
    }

    public void setSFTPChannel(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean isOpen() {
        return this.open;
    }

    public OutputStream getFile(String path, OutputStream os) throws SftpException {
        sftp.get(path, os);
        return os;
    }
}
