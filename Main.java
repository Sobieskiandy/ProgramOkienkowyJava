package org.example;
import java.awt.*;
import javax.swing.*;//Może być potrzebna
public class Main {
    public static void main(String[] args)
    {
        // Poniżej mamy niezbędny kod do uruchomienia aplikacji okienkowej.
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new MojaRamka();
            }
        });
    }
}