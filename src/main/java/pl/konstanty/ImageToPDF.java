package pl.konstanty;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageToPDF {
    public static void main(String[] args) throws IOException, DocumentException {

        JFrame frame = new JFrame();
        JButton select = new JButton("Select files");
        JButton convert = new JButton("Convert");
        JLabel label = new JLabel("File name: ");
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(150, 20));
        text.setText("New file");
        List<String> fileList = new ArrayList<>(); // temp file list

        frame.setLayout(new FlowLayout());
        frame.setTitle("JPG to PDF Converter"); // set title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // add close option
        frame.add(select); // add select to the frame
        frame.add(convert);
        frame.add(label);
        frame.add(text);
        frame.pack(); // compress everything
        frame.setVisible(true); // show frame

        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(); // JFilechooser
                fileChooser.setMultiSelectionEnabled(true); // enable File multi-selection
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"); // create filter for extensions
                fileChooser.setFileFilter(filter); // set extension filter
                fileChooser.setAcceptAllFileFilterUsed(false); // disable ALl files option

                int response = fileChooser.showOpenDialog(null); // open Dialog to pickup files
                if (response == JFileChooser.APPROVE_OPTION) {  // if showOpenDialog returns 0 it means that we clicked OK, otherwise its 1, APPROVE_OPTION = 0
                    File[] files = fileChooser.getSelectedFiles(); // get all files into File[] array
                    for (File file : files) {
                        fileList.add(file.getAbsolutePath());
                            System.out.println(file.getAbsolutePath()); // return file path for every file that was selected
                    }
                    System.out.println("Selected " + files.length + " files.");
                }
            }
        });

        convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Image img = Image.getInstance(fileList.get(0));  // we need to create initial value for image
                    Document document = new Document(img); // create a document file
                    PdfWriter.getInstance(document, new FileOutputStream( text.getText() +".pdf")); // create a pdfWriter
                    document.open(); // start document
                    for (String image : fileList) { // String filepath for every selected file
                        img = Image.getInstance(image); // create image instance
                        document.setPageSize(img); // set pdf page size of an image boundaries
                        document.newPage(); // create new page for pdf file
                        img.setAbsolutePosition(0,0); // set image file at the 0,0 position (upper-left corner)
                        document.add(img); // add image to document
                    }
                    document.close(); // finish and return PDF file
                } catch (IOException | DocumentException badElementException) {
                    badElementException.printStackTrace();
                }
                System.out.println("Succesfully converted!");
            }
        });

    }
}
