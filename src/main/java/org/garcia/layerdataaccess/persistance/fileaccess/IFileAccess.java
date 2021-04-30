package org.garcia.layerdataaccess.persistance.fileaccess;

import org.garcia.model.enums.ResourceType;
import org.garcia.model.Tour;

import java.io.File;
import java.util.List;

public interface IFileAccess {
    int createTourItemFile(String name, String url);
    int createLogFile(String text, Tour tour);
    List<File> searchFiles(String searchInput, ResourceType type);
    List<File> getAllFiles(ResourceType type);
}
