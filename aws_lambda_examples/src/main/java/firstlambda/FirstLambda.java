package firstlambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class FirstLambda implements RequestHandler<Geolocalization, String> {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);
    static Table table = dynamoDB.getTable("Geolocalization");

    public String handleRequest(Geolocalization geolocalization, Context context) {
        Item item = null;

        try{
        item = new Item().withPrimaryKey("id", geolocalization.getId())
                .withString("idClient", geolocalization.getIdClient())
                .withString("latitude", geolocalization.getLatitude())
                .withString("longitude", geolocalization.getLongitude())
                .withString("date", geolocalization.getDateUpdate().toString());
        table.putItem(item);
        }
        catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());
        }
        assert item != null;
        return ("statusCode : 200," +
                "data : {" + item.toJSON() + "}");
    }
}
