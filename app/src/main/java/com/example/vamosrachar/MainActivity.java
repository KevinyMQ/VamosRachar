package com.example.vamosrachar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText valueTxt; //Input do valor
    EditText numberOfPeopleTxt; //Input de número de pessoas
    TextView resultTxt; //Texto do resultado

    Float value; //Valor convertido
    Float numberOfPeople; //Numero de pessoas convertido
    Float result; //Resultado
    String message; //Mensagem TTS e Share

    TextToSpeech tts; //TTS
    Locale myLocale; //Locale pra mudar o idioma TTS
    Boolean isFilled; //Se os valores foram preenchidos corretamente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueTxt   = (EditText)findViewById(R.id.valueInput);
        numberOfPeopleTxt   = (EditText)findViewById(R.id.numberOfPeopleInput);
        resultTxt = (TextView) findViewById(R.id.ResultTxt);

        isFilled = false;

        //myLocale = new Locale("pt", "BR"); //Colocando voz em português
        valueTxt.addTextChangedListener(new TextWatcher() { //Checa mudança no valor
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Calculate(); //Chamando metodo que calcula toda vez que o input muda
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numberOfPeopleTxt.addTextChangedListener(new TextWatcher() { //Checa mudança no numero das pessoas
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Calculate(); //Chamando metodo que calcula toda vez que o input muda
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void Calculate(){ //Método que checa campos e calcula
        if(!valueTxt.getText().toString().equals("") && Float.parseFloat(valueTxt.getText().toString()) != 0 && !numberOfPeopleTxt.getText().toString().equals("") && Float.parseFloat(numberOfPeopleTxt.getText().toString()) != 0){
            isFilled = true;
            value = Float.parseFloat(valueTxt.getText().toString());
            numberOfPeople = Float.parseFloat(numberOfPeopleTxt.getText().toString());
            result = value/numberOfPeople;
            resultTxt.setText(String.format(getString((R.string.total)), result));
            message = String.format(getString(R.string.sentence), valueTxt.getText().toString(), numberOfPeopleTxt.getText().toString(), result.toString());
        }else{
            isFilled = false;
            message = getString(R.string.fail_msg);
            resultTxt.setText(message);
        }
    }

    /** Chamado quando o usuário clica no botão */
    public void TTS(View view) {
        if(isFilled){
            tts = new TextToSpeech(getApplicationContext(), status -> {
                //tts.setLanguage(myLocale);
                tts.speak(message, TextToSpeech.QUEUE_ADD, null);
            });
        }else{
            warningMessage();
        }
    }

    /** Chamado quando o usuário clica no botão */
    public void sendMessage(View view){
        if(isFilled){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_msg));
            startActivity(shareIntent);
        }else{
            warningMessage();
        }
    }

    public void warningMessage(){
        Toast.makeText(getApplicationContext(), getString(R.string.wrng_msg), Toast.LENGTH_SHORT).show();
    }



}