package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;

     /**    this method changes the value of the quantity of coffee
     *   @param number refers to the quantity
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(
                R.id.quantityTextView);
        quantityTextView.setText("" + number);

    }


    /**  this method reduces the value of the quantity of coffee
     * @param view refers to the quantityTextView
     */
    public void decrement(View view) {
        //makes sure the user can't order a negative quantity of coffee
        if (quantity>1) {
            quantity = quantity - 1;
        } else if (quantity == 1) {
            Toast.makeText(this, getString(R.string.toast1),Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**  this method increases the value of the quantity of coffee
     * @param view refers to the quantityTextView
     */
    public void increment(View view) {
        //makes sure the user can't order more than 50 cups of coffee at time
        if (quantity<50) {
            quantity = quantity + 1;
        } else if (quantity ==50) {
            Toast.makeText(this,getString(R.string.toast2), Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

     /**this method is called when the display order summary button is clicked
      * finds out which of the toppings the user has selected
     *calls the calculatePrice method to calculate the price
     *gets the user's name from the name EditText view
     *calls the createOrderSummary method to create the order summary, sending the variables above as input parameters
      * displays the order summary
     */

    public void submitOrder(View view) {
        //checks if the user wants whipped cream
        CheckBox whipped_cream = findViewById(R.id.whippedCream);
        boolean WC = whipped_cream.isChecked();

        //checks if the user wants chocolate
        CheckBox wantsChocolate =findViewById(R.id.Chocolate);
        boolean choco = wantsChocolate.isChecked();

        //gets the name entered by the user
        EditText getName = findViewById(R.id.name);
        Editable userName = getName.getText();
        int price = calculatePrice(choco, WC);
        String orderSummary = createOrderSummary(price, WC,choco,userName);

        displayMessage(orderSummary);

    }

    /**
     * this method is called when the order button is clicked
     * It gets the order summary from the text view and sends it as an email
     * w
     */
    public void sendOrder(View view) {
        TextView OrderSummaryTextView = findViewById(R.id.orderSummaryTextView);
        String orderSummary = (String) OrderSummaryTextView.getText();
        EditText getName = findViewById(R.id.name);
        Editable userName = getName.getText();

        Intent send = new Intent(Intent.ACTION_SENDTO);
        send.setData(Uri.parse("mailto:"));
        send.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + userName);
        send.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (send.resolveActivity(getPackageManager()) != null) {
            startActivity(send);
        }
    }
    /**
     *creates the order summary
     * @param price is the price of the coffee
     * @param WC is whether or not the user wants whipped cream
     * @param choco is whether or not the user wants chocolate
     * @param userName is the name entered by the user
     * @return orderSummary is the summary of the order
     */
    private String createOrderSummary(int price, boolean WC, boolean choco, Editable userName) {
        String orderSummary = ("Name: " + userName + "\nWants whipped cream? " + WC + "\nWants Chocolate? "+ choco +"\nQuantity: " + quantity);
        orderSummary += ("\nTotal:" + (NumberFormat.getCurrencyInstance().format(price)) + "\nThank you!");
        return(orderSummary);
    }

    /**
     * changes the text displayed in the orderSummaryTextView
     * @param message is the new text to be displayed
     */
    private void displayMessage(String message) {
        TextView ordersummarytextview = findViewById(
                R.id.orderSummaryTextView);
        ordersummarytextview.setText("" + message);
    }

    /**
     * calculates the price of the order
     * @param choco is whether or not the user wants chocolate
     * @param WC is whether or not the use wants whipped cream
     * @return the total price
     */
    private int calculatePrice(boolean choco, boolean WC) {
        int price = 150;
        //adds 50 naira is the user wants whipped cream
        if (WC) {
            price += 50;
        }
        //adds 50 naira if the user wants chocolate
        if(choco) {
            price += 50;
        }
        //calculates the total price and returns it
        return(quantity * price);
    }

}
