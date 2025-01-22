package fr.courel.avokeeper;

import com.zaxxer.hikari.HikariDataSource;
import fr.courel.avokeeper.service.ClientService;
import fr.courel.avokeeper.view.ClientView;
import javax.sql.DataSource;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialOceanicTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class AvokeeperApplication {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    @Value("${spring.config.location:default}")
    private String configLocation;

    public static void main(String[] args) {
        SpringApplication.run(AvokeeperApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(ApplicationContext context) {
        return args -> {
            try {
                UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialOceanicTheme()));
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            if (java.awt.GraphicsEnvironment.isHeadless()) {
                System.err.println("Erreur : L'environnement graphique n'est pas disponible !");
                System.exit(1);
            }
            // Lancer l'interface Swing dans l'Event Dispatch Thread
            ClientService clientService = context.getBean(ClientService.class);
            SwingUtilities.invokeLater(() -> {
                ClientView ui = new ClientView(clientService);
                ui.setVisible(true);
            });
        };
    }

    @Bean
    CommandLineRunner checkDataSource() {
        return args -> {
            System.out.println("Datasource Bean: " + dataSource);
            if (dataSource instanceof HikariDataSource) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                System.out.println("HikariDataSource URL: " + hikariDataSource.getJdbcUrl());
                System.out.println("HikariDataSource Username: " + hikariDataSource.getUsername());
            } else {
                System.out.println("Datasource is not an instance of HikariDataSource.");
            }
        };
    }

    @Bean
    CommandLineRunner listProperties() {
        return args -> {
            System.out.println("spring.datasource.url: " + environment.getProperty("spring.datasource.url"));
            System.out.println("spring.datasource.username: " + environment.getProperty("spring.datasource.username"));
            System.out.println("spring.datasource.password: " + environment.getProperty("spring.datasource.password"));
            System.out.println("spring.datasource.driver-class-name: " + environment.getProperty("spring.datasource.driver-class-name"));
        };
    }

    @Bean
    CommandLineRunner checkConfigLocation() {
        return args -> {
            System.out.println("Config Location: " + configLocation);
        };
    }
}
