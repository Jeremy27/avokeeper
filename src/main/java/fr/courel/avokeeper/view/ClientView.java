package fr.courel.avokeeper.view;

import fr.courel.avokeeper.model.Client;
import fr.courel.avokeeper.service.ClientService;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import mdlaf.components.button.MaterialButtonUI;

public class ClientView extends JFrame {
    private ClientService clientService;

    private JTextField nameField;
    private JTextField emailField;
    private JTextArea clientsArea;

    public ClientView(ClientService clientService) {
        this.clientService = clientService;
        setTitle("Gestion des Clients");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel pour ajouter un client
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Nom :"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email :"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        JButton addButton = new JButton("Ajouter Client");
        addButton.setUI(new MaterialButtonUI());
        addButton.addActionListener(new AddClientAction());
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Zone pour afficher les clients
        clientsArea = new JTextArea();
        clientsArea.setEditable(false);
        add(new JScrollPane(clientsArea), BorderLayout.CENTER);

        // Bouton pour afficher tous les clients
        JButton showClientsButton = new JButton("Afficher Clients");
        showClientsButton.setUI(new MaterialButtonUI());
        showClientsButton.addActionListener(new ShowClientsAction());
        add(showClientsButton, BorderLayout.SOUTH);
    }

    private class AddClientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String email = emailField.getText();
            Client client = new Client();
            client.setFirstName(name);
            client.setEmail(email);
            clientService.addClient(client);
            nameField.setText("");
            emailField.setText("");
            JOptionPane.showMessageDialog(ClientView.this, "Client ajouté !");
        }
    }

    private class ShowClientsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> clients = clientService.getAllClients();
            clientsArea.setText("");
            for (Client client : clients) {
                clientsArea.append("Nom: " + client.getFirstName()+ ", Email: " + client.getEmail() + "\n");
            }
        }
    }
}
