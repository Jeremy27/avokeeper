package fr.courel.avokeeper.repository;

import fr.courel.avokeeper.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Des méthodes supplémentaires peuvent être ajoutées ici si nécessaire
}
