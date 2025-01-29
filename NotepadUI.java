import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
public class NotepadUI
{
   public static void main(String gg[])
   {
    JFrame frame = new JFrame("Notepad Clone");
    frame.setSize(800,600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  ImageIcon icon = new ImageIcon("logo1.jpg"); // Ensure logo.png is in the project folder
        frame.setIconImage(icon.getImage());
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("file");
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");
    fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();  // Adds a separator line
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Create Text Area
        JTextArea textArea = new JTextArea();
 textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(textArea);

           saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(textArea.getText());
                        JOptionPane.showMessageDialog(frame, "File saved successfully!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

       newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textArea.getText().isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to create a new file? Unsaved changes will be lost.", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        textArea.setText(""); // Clear text area
                    }
                } else {
                    textArea.setText(""); // Directly clear if empty
                }
            }
        });
    exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textArea.getText().isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to exit? Unsaved changes will be lost.", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        System.exit(0); // Close application
                    }
                } else {
                    System.exit(0); // Directly exit if no text
                }
            }
        });
  
      openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open File Dialog
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        textArea.setText(content.toString()); // Display file content in JTextArea
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Set Layout and Add Components
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Show Frame
        frame.setVisible(true);
  
    }
}