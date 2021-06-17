package com.meli.ipgeolocalization.usecases.implementations;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;
import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import com.meli.ipgeolocalization.usecases.model.CountryDistance;
import com.meli.ipgeolocalization.utils.JacksonUtils;
import com.meli.ipgeolocalization.utils.StatsUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "dynamoHistory")
public class DynamoQueryHistoryStorage implements QueryHistoryStorage {

  private final Logger logger = LoggerFactory.getLogger(DynamoQueryHistoryStorage.class);

  @Override
  public void registerCountryDistance(String country, Double distance) {

    try {
      CountryDistance dynamoItem = getDynamoItem(country);
      if(dynamoItem == null) {
        dynamoItem = new CountryDistance(country, distance);
        putDynamoItem(dynamoItem);
      } else {
        updateDynamoItem(dynamoItem);
      }
    } catch(Exception e) {
      logger.error("An error occurred while register trace in dynamo table, caused by: {}", e.getMessage());
      throw new BusinessException("Error al registrar la traza consultada, por favor intente más tarde");
    }

  }

  @Override
  public StatsResponse getConsumptionStats() {
    List<CountryDistance> dynamoHistory = getDynamoHistory();
    Double max = StatsUtils.getMax(dynamoHistory);
    Double min = StatsUtils.getMin(dynamoHistory);
    Double average = StatsUtils.getAverage(dynamoHistory);
    return new StatsResponse(max, min, average);
  }

  private CountryDistance getDynamoItem(String country) {
    CountryDistance countryDistance = null;
    Table table = getDynamoTable();
    PrimaryKey primaryKey = new PrimaryKey("country", country);
    Item item = table.getItem(primaryKey);
    if(item != null) {
      ObjectMapper mapper = new ObjectMapper();
      countryDistance = mapper.convertValue(item.asMap(), CountryDistance.class);
      logger.info("Retrieved Item: {}", countryDistance);
    }
    return countryDistance;
  }

  private void putDynamoItem(CountryDistance dynamoItem) {
    Table table = getDynamoTable();
    Item item = new Item()
        .with("country", dynamoItem.getCountry())
        .with("distance", dynamoItem.getDistance())
        .with("invocations", dynamoItem.getInvocations());
    table.putItem(item);
  }

  private void updateDynamoItem(CountryDistance dynamoItem) {
    Table table = getDynamoTable();
    UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("country", dynamoItem.getCountry())
        .withUpdateExpression("ADD invocations :inc")
        .withValueMap(new ValueMap().withNumber(":inc", 1))
        .withReturnValues(ReturnValue.UPDATED_NEW);
    table.updateItem(updateItemSpec);
  }

  private List<CountryDistance> getDynamoHistory() {
    try {
      List<CountryDistance> dynamoHistory = new ArrayList<>();
      Table table = getDynamoTable();
      ScanSpec scanSpec = new ScanSpec();
      ItemCollection<ScanOutcome> items = table.scan(scanSpec);
      for (Item item : items) {
        ObjectMapper mapper = new ObjectMapper();
        CountryDistance countryDistance = mapper.convertValue(item.asMap(), CountryDistance.class);
        dynamoHistory.add(countryDistance);
      }
      logger.info(JacksonUtils.getPlainJsonJson(dynamoHistory));
      return dynamoHistory;
    }
    catch (Exception e) {
      logger.error("An error occurred while scan dynamo table, caused by: {}", e.getMessage());
      throw new BusinessException("Error al consultar las estadisticas, por favor intente más tarde");
    }
  }

  private Table getDynamoTable() {
    String TABLE_NAME = "country-distance-invocations";
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDB dynamoDB = new DynamoDB(client);
    return dynamoDB.getTable(TABLE_NAME);
  }
}
