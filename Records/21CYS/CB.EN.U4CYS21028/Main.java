package com.amrita.jpl.cys21028.endsem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


class File {
    private String fileName;
    /**
     * @param fileName takes in file name
     */
    
    private int fileSize;
    /**
     * @param fileSize takes in filesize
     *
     */

    public File(String fileName, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public void displayFileDetails() {
        System.out.println("File Name: " + fileName);
        System.out.println("File Size: " + fileSize + " KB");
    }
}

class Document extends File {
    private String documentType;

    public Document(String fileName, int fileSize, String documentType) {
        super(fileName, fileSize);
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Override
    public void displayFileDetails() {
        super.displayFileDetails();
        System.out.println("Document Type: " + documentType);
    }
}

class Image extends File {
    private String resolution;

    public Image(String fileName, int fileSize, String resolution) {
        super(fileName, fileSize);
        this.resolution = resolution;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public void displayFileDetails() {
        super.displayFileDetails();
        System.out.println("Resolution: " + resolution);
    }
}

class Video extends File {
    private String duration;

    public Video(String fileName, int fileSize, String duration) {
        super(fileName, fileSize);
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public void displayFileDetails() {
        super.displayFileDetails();
        System.out.println("Duration: " + duration);
    }
}

interface FileManager {
    void addFile(File file);

    void deleteFile(String fileName);

    ArrayList<File> getFiles();
}

class FileManagerImpl implements FileManager {
    private ArrayList<File> files;

    public FileManagerImpl() {
        files = new ArrayList<>();
    }

    public void addFile(File file) {
        files.add(file);
    }

    public void deleteFile(String fileName) {
        File fileToDelete = null;
        for (File file : files) {
            if (file.getFileName().equals(fileName)) {
                fileToDelete = file;
                break;
            }
        }
        if (fileToDelete != null) {
            files.remove(fileToDelete);
        }
    }

    public ArrayList<File> getFiles() {
        return files;
    }
}

class FileManagementSystemUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField fileNameField, fileSizeField, documentTypeField, resolutionField, durationField;
    private JButton addButton, deleteButton, refreshButton;
    private FileManager fileManager;

    public FileManagementSystemUI(FileManager fileManager) {
        this.fileManager = fileManager;
        createUI();
    }

    private void createUI() {
        frame = new JFrame("File Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("File Name");
        tableModel.addColumn("File Size ");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        fileNameField = new JTextField(10);
        fileSizeField = new JTextField(10);
        documentTypeField = new JTextField(10);
        resolutionField = new JTextField(10);
        durationField = new JTextField(10);

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("File Name:"));
        inputPanel.add(fileNameField);
        inputPanel.add(new JLabel("File Size (KB):"));
        inputPanel.add(fileSizeField);
        inputPanel.add(new JLabel("Document Type:"));
        inputPanel.add(documentTypeField);
        inputPanel.add(new JLabel("Resolution:"));
        inputPanel.add(resolutionField);
        inputPanel.add(new JLabel("Duration:"));
        inputPanel.add(durationField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFile();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFile();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshFiles();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void addFile() {
        String fileName = fileNameField.getText();
        int fileSize = Integer.parseInt(fileSizeField.getText());

        if (!documentTypeField.getText().isEmpty()) {
            String documentType = documentTypeField.getText();
            Document document = new Document(fileName, fileSize, documentType);
            fileManager.addFile(document);
        } else if (!resolutionField.getText().isEmpty()) {
            String resolution = resolutionField.getText();
            Image image = new Image(fileName, fileSize, resolution);
            fileManager.addFile(image);
        } else if (!durationField.getText().isEmpty()) {
            String duration = durationField.getText();
            Video video = new Video(fileName, fileSize, duration);
            fileManager.addFile(video);
        } else {
            File file = new File(fileName, fileSize);
            fileManager.addFile(file);
        }

        clearInputFields();
        updateTable();
    }

    private void deleteFile() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String fileName = (String) table.getValueAt(selectedRow, 0);
            fileManager.deleteFile(fileName);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a file to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshFiles() {
        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        ArrayList<File> files = fileManager.getFiles();
        for (File file : files) {
            tableModel.addRow(new Object[]{file.getFileName(), file.getFileSize()});
        }
    }

    private void clearInputFields() {
        fileNameField.setText("");
        fileSizeField.setText("");
        documentTypeField.setText("");
        resolutionField.setText("");
        durationField.setText("");
    }

    public static void main(String[] args) {
        FileManager fileManager = new FileManagerImpl();
        FileManagementSystemUI systemUI = new FileManagementSystemUI(fileManager);
    }
}
