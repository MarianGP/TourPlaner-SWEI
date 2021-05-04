package org.garcia.layerdataaccess.common.fileaccess;

import org.garcia.model.Tour;
import org.garcia.model.enums.ResourceType;

import java.io.File;
import java.util.List;

public class FileAccess implements IFileAccess {

    @Override
    public int createTourFile(String name, String url) {
        return 0;
    }

    @Override
    public int createTourLogFile(String text, Tour tour) {
        return 0;
    }

    @Override
    public List<File> searchFiles(String searchInput, ResourceType type) {
        return null;
    }

    @Override
    public List<File> getAllFiles(ResourceType type) {
        return null;
    }
}
