package com.starter.fullstack.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.fullstack.api.Inventory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InventoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private Inventory inventory;

  @Before
  public void setup() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setId("ID");
    this.inventory.setName("TEST");
    this.inventory.setProductType("hops");
    // Sets the Mongo ID for us
    this.inventory = this.mongoTemplate.save(this.inventory);
  }

  @After
  public void teardown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test findInventories endpoint.
   * @throws Throwable see MockMvc
   */
  @Test
  public void findInventories() throws Throwable {
    this.mockMvc.perform(get("/inventory")
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json("[" + this.objectMapper.writeValueAsString(inventory) + "]"));
  }

  /**
   * Test delete endpoint.
   * @throws Throwable see MockMvc
   */
  @Test
  public void deleteInventory() throws Throwable {
    this.mockMvc.perform(delete("/inventory/" + this.inventory.getId())
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());

    Assert.assertEquals(0, this.mongoTemplate.findAll(Inventory.class).size());
  }
}