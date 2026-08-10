package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inventory Controller.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  /**
   * Find Products.
   * @return List of Product.
   */
  @GetMapping
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }

  /**
   * Save Inventory.
   * @param inventory inventory.
   * @return Inventory.
   */
  @PostMapping
  public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

  /**
   * Delete Inventory.
   * @param id id.
   * @return Inventory.
   */
  @DeleteMapping("/{id}")
  public Inventory deleteInventory(@PathVariable String id) {
    // Using a path variable since this deletes a single item by id.
    Optional<Inventory> inventory = this.inventoryDAO.delete(id);
    return inventory.orElse(null);
  }

  /**
   * Update Inventory.
   * @param id id.
   * @param inventory inventory.
   * @return Inventory.
   */
  @PutMapping("/{id}")
  public Inventory updateInventory(@PathVariable String id, @Valid @RequestBody Inventory inventory) {
    // Mirrors deleteInventory's path-variable pattern, since this also targets a single item by id.
    Optional<Inventory> updatedInventory = this.inventoryDAO.update(id, inventory);
    return updatedInventory.orElse(null);
  }
}