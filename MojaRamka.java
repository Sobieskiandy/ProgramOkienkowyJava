//Nięzbędne biblioteki
package org.example;
//Import, eksport plików oraz try,catch
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;
import java.io.IOException;
//
import java.awt.*;//potrzebne do używania setLayout()
import javax.swing.*;
import java.util.Set;//Set
import java.lang.String;//String
import java.util.HashSet;//HashSet
//
import javax.swing.JOptionPane;//tworzy okienko "Popup"
import java.awt.event.ActionEvent;//obsługa guzików
import java.awt.event.ActionListener;//obsługa guzików

public class MojaRamka extends JFrame{
    //zmienne tylko we wstępie
    JLabel l = new JLabel("<html>Witaj w programie!<br/>Wybierz opcje:<br/>1.Wczytanie z pliku, podaj ścieżkę do pliku<br/>2. Wpisz słowo (4 literowe, duże litery np:ABCD)<br/>Po wpisaniu niezbędnych danych kliknij <kbd>ENTER</kbd></html>", SwingConstants.CENTER);
    JButton btn = new JButton("ENTER");
    String liczby[] = {"1","2"};
    String wyniki = "";
    JComboBox cb;
    //zmienne tylko w rozwinięciu
    JLabel zapisano = new JLabel("<html><p style='color:green'>Zapisano</p></html>", SwingConstants.CENTER);
    JLabel szuk = new JLabel("Znaleziono"); JLabel szuk1 = new JLabel("Nie znaleziono");
    JButton szukaj = new JButton("Szukaj");
    JButton zapis = new JButton("Zapis");
    private int Rezultat = 0;
    JLabel rezultat = new JLabel("Rezultat("+Rezultat+")");
    JTextField szukane = new JTextField(15), sciezka = new JTextField(15);
    JTextField tekst = new JTextField(15);
    //zmienne przejściowe
    JLabel errP = new JLabel("<html><p style='color:red'>PLIK NIE ISTNIEJE</p></html>", SwingConstants.CENTER);
    JLabel errPu = new JLabel("<html><p style='color:red'>PLIK JEST PUSTY</p></html>", SwingConstants.CENTER);
    JLabel err = new JLabel("<html><p style='color:red'>BŁĘDNE SŁOWO</p></html>", SwingConstants.CENTER);
    File myObj; File myObj1;

    //Dodatkowe zmienne do algorytmu
    private StringBuilder permutacje = new StringBuilder();
    private Set<String> secik = new HashSet<>();

   public void algorytm(String slowo, String prefix) {
       if (slowo.length() == 0 && !secik.contains(prefix)) {
           secik.add(prefix);
           permutacje.append(prefix).append(", ");
           Rezultat++;
           return;
       }
       for (int i = 0; i < slowo.length(); i++) {
           String slowo1 = slowo.substring(0, i) + slowo.substring(i + 1);
           String prefix1 = prefix + slowo.charAt(i);
           algorytm(slowo1, prefix1);
       }
   }
    public void a() {
        getContentPane().removeAll(); // usuwa wszystkie obiekty z okna JFrame.
        setLayout(new FlowLayout());
        zapisano.setVisible(false);
        errP.setVisible(false);
        JLabel output = new JLabel(wyniki);
        add(szukaj);add(szukane);
        szuk.setVisible(false);szuk1.setVisible(false);
        add(szuk);add(szuk1);
        rezultat = new JLabel("Rezultat("+Rezultat+")");
        add(rezultat);
        add(output);

        add(zapis);add(sciezka);
        add(zapisano);add(errP);

        szukaj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==szukaj) {
                    String input = szukane.getText();
                    String tex = output.getText();
                    System.out.println(input);

                    if(tex.contains(input)) {System.out.println("Ma");szuk.setVisible(true);szuk1.setVisible(false);revalidate();repaint();}
                    else {System.out.println("Nie ma");szuk.setVisible(false);szuk1.setVisible(true);revalidate();repaint();}

                }
            }
        });
        zapis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==zapis) {
                    String input = sciezka.getText();
                    myObj1 = new File(input);
                    if(myObj1.exists())
                    {
                        System.out.println("Plik istnieje");

                        try {
                            File file = new File(input);
                            Scanner scanner = new Scanner(file);
                            boolean isEmpty = !scanner.hasNextLine();
                            System.out.println("Is empty: "+isEmpty);
                            scanner.close();
                                if(isEmpty)
                                {
                                    try {
                                    FileWriter myWriter = new FileWriter(input);
                                    myWriter.write(wyniki);
                                    myWriter.close();
                                    System.out.println("Successfully wrote to the file.");
                                    zapisano.setVisible(true);errP.setVisible(false);revalidate();repaint();
                            } catch (IOException f) {
                                System.out.println("Blad z zapisem.");
                                f.printStackTrace();
                            }
                            }else{
                                    int wybor;
                                    Object[] options = { "Tak", "Nie"};
                                        wybor = JOptionPane.showOptionDialog(null, "Plik nie jest pusty. Operacja spowoduje utratę danych z pliku. Czy kontunuować?", "Ostrzeżenie",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                        System.out.println("Plik nie pusty, czy potwierdzasz operację?");

                                    switch(wybor)
                                    {
                                        case 0:
                                            System.out.println("Zapisywać");
                                            try {
                                                FileWriter myWriter = new FileWriter(input);
                                                myWriter.write(wyniki);
                                                myWriter.close();
                                                System.out.println("Successfully wrote to the file.");
                                                zapisano.setVisible(true);errP.setVisible(false);revalidate();repaint();
                                            } catch (IOException f) {
                                                System.out.println("Blad z zapisem.");
                                                f.printStackTrace();
                                            }
                                            break;
                                        case 1:
                                            System.out.println("Nie zapisywać");
                                            // do something for Option 2
                                            break;
                                        default:
                                            break;
                                    }
                                }
                        } catch (FileNotFoundException g) {
                            System.out.println("Blad z isEmpty.");
                            g.printStackTrace();
                        }
                        //if((myObj.getPath().equals(input)==myObj1.getPath().equals(input))||(myObj.getPath()==null)) System.out.println("Nazwy równe");else System.out.println("Nazwy nie równe");
                    }
                    else{System.out.println("Plik nie istnieje");zapisano.setVisible(false);errP.setVisible(true);revalidate();repaint();}
                }

            }
        });


        revalidate(); // przeładowuje okno JFrame.
        repaint(); //odświeża GUI aplikacji.
    }
    public MojaRamka() {
        super("The Projtktator 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocation(300, 300);
        setLayout(new FlowLayout());

        add(l);
        cb = new JComboBox(liczby);
        cb.setBounds(50,50,90,20);
        add(cb);
        tekst = new JTextField(15);
        add(tekst);
        add(btn);
        err.setVisible(false);add(err);
        errP.setVisible(false);add(errP);
        errPu.setVisible(false);add(errPu);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==btn) {
                    String input = tekst.getText();
                    String opcja = (String) cb.getSelectedItem();
                    errP.setVisible(false);
                    if(opcja=="2"){
                        errPu.setVisible(false);
                        if(input.matches("[A-Z]{4}$"))
                        {System.out.println("INPUT2 POPRAWNE");err.setVisible(false);
                            wyniki=input; JLabel output= new JLabel(wyniki);
                            algorytm(wyniki,"");
                            wyniki=permutacje.toString();
                            wyniki= wyniki.substring(0, wyniki.length() - 2);
                            a();
                        }
                        else{ System.out.println("NIE ZGADZA SIE");err.setVisible(true);}
                    }
                    if(opcja=="1")
                    {
                        myObj = new File(input);
                        err.setVisible(false);
                        errP.setVisible(false);
                        if (myObj.exists())
                        {
                            System.out.println("Plik istnieje");errP.setVisible(false);

                            try {
                                File file = new File(input);
                                Scanner scanner = new Scanner(file);
                                boolean isEmpty = !scanner.hasNextLine();
                                System.out.println("Is empty: "+isEmpty);
                                scanner.close();
                                if(isEmpty)
                                {
                                    errPu.setVisible(true);
                                }
                                else{
                                    try {
                                        errPu.setVisible(false);
                                        File myObj = new File(input);
                                        Scanner myReader = new Scanner(myObj);
                                        while (myReader.hasNext()) {
                                            wyniki += myReader.next()+" ";
                                            if (wyniki.length() == 4-Rezultat)
                                                System.out.println(wyniki.length());
                                            Rezultat++;
                                        }

                                        System.out.println(wyniki);
                                        myReader.close();
                                        a();
                                    } catch (FileNotFoundException g) {
                                        System.out.println("An error occurred.");
                                        g.printStackTrace();
                                    }
                                }
                            } catch (FileNotFoundException g) {
                                errPu.setVisible(false);
                                System.out.println("Blad z isEmpty.");
                                g.printStackTrace();
                            }
                        }
                        else
                        {
                            System.out.println("Plik nie istnieje");errP.setVisible(true);
                        }
                    }
                }
            }
        });
                /** Przestawienie obiektów, tak by się wyświetlały pionowo, nie poziomo
                 *  JFrame frame = new JFrame();
                 *         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 *         frame.setLayout(new FlowLayout());
                 *
                 *         JLabel szukaj = new JLabel("Szukaj:");
                 *         JLabel szukane = new JLabel("Tekst");
                 *         JLabel szuk = new JLabel("0");
                 *
                 *         JLabel rezultat = new JLabel("Rezultat(XX)");
                 *
                 *         JPanel labelPanel = new JPanel();
                 *         labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
                 *
                 *         labelPanel.add(szukaj);
                 *         labelPanel.add(szukane);
                 *         labelPanel.add(Box.createVerticalStrut(10)); // insert 10 pixels of vertical space
                 *         labelPanel.add(szuk);
                 *         labelPanel.add(Box.createVerticalStrut(10)); // insert another 10 pixels of vertical space
                 *         labelPanel.add(rezultat);
                 *
                 *         frame.getContentPane().add(labelPanel);
                 *         frame.pack();
                 *         frame.setVisible(true);
                 *
                 * */


        setVisible(true);
    }
}
