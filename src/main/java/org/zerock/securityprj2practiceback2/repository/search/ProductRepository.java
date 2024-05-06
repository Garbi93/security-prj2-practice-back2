package org.zerock.securityprj2practiceback2.repository.search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.securityprj2practiceback2.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
