package info.doetsch.filetransfer.services;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class SupplierConfiguration  {
    private String supplierName;
    @Id
    private String id;
    private String destinationDirectory;
    private String destinationHost;
    private String fileNameTemplate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierConfiguration that = (SupplierConfiguration) o;
        return Objects.equals(supplierName, that.supplierName) &&
                Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "SupplierConfiguration{" +
                "supplierName='" + supplierName + '\'' +
                ", id='" + id + '\'' +
                ", destinationDirectory='" + destinationDirectory + '\'' +
                ", destinationHost='" + destinationHost + '\'' +
                ", fileNameTemplate='" + fileNameTemplate + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierName, id);
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    public void setDestinationHost(String destinationHost) {
        this.destinationHost = destinationHost;
    }

    public String getFileNameTemplate() {
        return fileNameTemplate;
    }

    public void setFileNameTemplate(String fileNameTemplate) {
        this.fileNameTemplate = fileNameTemplate;
    }
}