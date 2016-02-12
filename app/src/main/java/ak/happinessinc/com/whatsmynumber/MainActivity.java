package ak.happinessinc.com.whatsmynumber;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    EditText inputEditText;
    TextView outputTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputEditText=(EditText) findViewById(R.id.mainActivity_inputEditText);
        outputTextView=(TextView) findViewById(R.id.mainActivity_outputTextView);

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable s) {
                String number=inputEditText.getText().toString().replaceAll(",","");
                if(number.length()>0) {
                    try {


                        if (number.length() <= 19) {
                            inputEditText.setTextColor(Color.WHITE);
                            long num = Long.parseLong(number);
                            outputTextView.setText(convert(num));
                        }
                        if (number.length() <= 36 && number.length() > 19) {
                            if(number.length()>25){
                                inputEditText.setTextColor(Color.YELLOW);
                            }
                            if(number.length()>30){
                                inputEditText.setTextColor(Color.RED);
                            }
                            String substr2 = number.substring(number.length() - 18);
                            String substr1 = number.substring(0, number.length() - 18);

                            String startstring = convertMoreThan_quintillion(Long.parseLong(substr1));
                            String end = convert(Long.parseLong(substr2));
                            outputTextView.setText(startstring + "" + end);
                        }
                        if (number.length() > 36) {
                            Toast.makeText(getApplicationContext(),"Androids have feelings too, please have mercy!! ('_') ",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (NumberFormatException n){
                        Toast.makeText(getApplicationContext(),"OMG....this is too much to handle !!!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    outputTextView.setText("no numbers...");


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final String[] thousandsAndMore = {
            "",
            " thousand,",
            " million,",
            " billion,",
            " trillion,",
            " quadrillion,",
            " quintillion,",
            " sextillion,",
            " septillion,",
            " octillion,",
            " nonillion,",
            " decillion,",
            " undecillion,",
            " duodecillion,"
    };

    private static final String[] tens = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };

    private static final String[] decis = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };

    private String convertLessThan_oneThousand(int number,int position) {
        String current;

        if (number % 100 < 20){

                    current = decis[number % 100];
                    number /= 100;

        }
        else {
                current = decis[number % 10];
                number /= 10;

                    current = tens[number % 10] + current;
                    number /= 10;

        }
        if (number == 0) return current;
        if (position<1){
            return decis[number] + " hundred and " + current;
        }
        return decis[number] + " hundred " + current;
    }



    public String convert(long number) {

        if (number == 0) { return "zero"; }

        String current = "";
        int place = 0;

        do {
            long n = number % 1000;
            if (n != 0){

                    String s = convertLessThan_oneThousand((int) n,place);
                    current = s + thousandsAndMore[place] + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);

        return (current).trim();
    }


    public String convertMoreThan_quintillion(long number) {

        if (number == 0) { return "zero"; }

        String prefix = "";

        if (number < 0) {
            number = -number;
            prefix = "negative";
        }

        String current = "";
        int place = 0;

        do {
            long n = number % 1000;
            if (n != 0){
                String s = convertLessThan_oneThousand((int)n,5);
                current = s + thousandsAndMore[place+6] + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);

        return (prefix + current).trim();
    }


}
