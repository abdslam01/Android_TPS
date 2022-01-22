package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HashMap<String, Button> Btns;
    private TextView inputText;
    private HashMap<String, String> InputOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creation et Initialisation de HashMap 'InputOutPut'
        InputOutput = new HashMap<String, String>();
        for(String e : new String[]{"FirstInputOutput", "SecondInputOutput", "OprType"})
            InputOutput.put(e,null);
        InputOutput.put("State", "false");

        // GRAB THE TextView
         inputText = (TextView) findViewById(R.id.textView2);

        // GRAB AND SET LISTENERS TO ALL BUTTON
        Btns=new HashMap<String,Button>();
        String BtnsName[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Plus", "Minus", "Mult", "Div", "C", "Comma", "Equal"};
        for (String Name : BtnsName) {
            int id = getResources().getIdentifier("button"+Name, "id", getPackageName());
            Btns.put("button"+Name, (Button) findViewById(id));
            Btns.get("button"+Name).setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonC:
                inputText.setText(Methods.cancelLastChar(inputText.getText().toString()));
                break;
            case R.id.buttonPlus:
            case R.id.buttonMinus:
            case R.id.buttonMult:
            case R.id.buttonDiv:
                 // insertion des valeur a calculer
                if(!inputText.getText().toString().isEmpty()) {
                    if(InputOutput.get("FirstInputOutput") == null)
                        InputOutput.put("FirstInputOutput",inputText.getText().toString());
                    else
                        InputOutput.put("SecondInputOutput",inputText.getText().toString());
                }
                 // definir le type de l'operation a effectuer
                InputOutput.put("OprType", ((Button) v).getText().toString());
                InputOutput.put("State", "true");
                break;
            case R.id.buttonEqual:
                // initialise le 2eme input
                InputOutput.put("SecondInputOutput", inputText.getText().toString());

                //si tous les champs de HashMap 'InputOutput' non nul, effectuer l'operation de calcule et l'afficher
                if(!InputOutput.containsValue(null)) {
                    inputText.setText( Methods.operation(InputOutput.get("FirstInputOutput"), InputOutput.get("SecondInputOutput"), InputOutput.get("OprType")));
                    /*
                     * initialiser les input, et vider le champs input
                     */
                    InputOutput.put("FirstInputOutput", null);
                    InputOutput.put("SecondInputOutput", null);
                }
                break;
            default:
                 // cette partie executer si l'utilisateur cliquer sur une des buttons ci-apres:
                 // 		[0,1,2,3,4,5,6,7,8,9, {,}]
                 if(InputOutput.get("State").equals("true")) { // if operation button [+-*/] is clicked previously
                    InputOutput.put("State", "false");
                    inputText.setText("");
                 }
                if(inputText.getText().toString().isEmpty()) {
                    if(v.getId() != Btns.get("buttonComma").getId() && v.getId() != Btns.get("button0").getId())
                        inputText.setText(String.format(
                                "%s%s", inputText.getText().toString(), ((Button) v).getText().toString()));
                }else if(v.getId() != Btns.get("buttonComma").getId()
                        || ( v.getId() == Btns.get("buttonComma").getId() && !Methods.isContains(inputText.getText().toString(), '.') )
                )
                    inputText.setText(String.format(
                            "%s%s", inputText.getText().toString(), ((Button) v).getText().toString()));
        }
    }
}