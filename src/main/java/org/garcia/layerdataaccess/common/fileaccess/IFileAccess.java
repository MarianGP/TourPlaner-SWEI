package org.garcia.layerdataaccess.common.fileaccess;

import org.garcia.model.enums.ResourceType;
import org.garcia.model.Tour;

import java.io.File;
import java.util.List;

public interface IFileAccess {
    int createTourFile(String name, String url);
    int createTourLogFile(String text, Tour tour);
    List<File> searchFiles(String searchInput, ResourceType type);
    List<File> getAllFiles(ResourceType type);
}
