package com.example.tabhostgogo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Tab1EventPage extends AppCompatActivity {
    Intent intent,intent2;
    String name,num;
    EditText nameText,numText;
    Button button;
    Button edit;
    InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_event_page);
        intent=getIntent();

        name=intent.getStringExtra("name");
        num=intent.getStringExtra("number");

        inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);


        nameText=(EditText) findViewById(R.id.name);
        numText=(EditText) findViewById(R.id.num);
        nameText.setFocusable(false);
        nameText.setClickable(false);
        numText.setFocusable(false);
        numText.setClickable(false);
        button=(Button)findViewById(R.id.button);
        edit=(Button)findViewById(R.id.button2);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });

        edit.setOnClickListener(new Button.OnClickListener(){
            int flag=0;
            @Override
            public void onClick(View view){
                if (flag==0){
                    edit.setText("save"); //버튼 text 바꾸기

                    //edit 기능 구현 editable true
                    nameText.setFocusableInTouchMode(true);
                    nameText.setFocusable(true);
                    nameText.setClickable(true);
                    numText.setFocusableInTouchMode(true);
                    numText.setFocusable(true);
                    numText.setClickable(true);
                    inputMethodManager.showSoftInput(nameText, 0);
                    inputMethodManager.showSoftInput(numText, 0);
                    inputMethodManager.hideSoftInputFromWindow(nameText.getWindowToken(),0);
                    inputMethodManager.hideSoftInputFromWindow(numText.getWindowToken(),0);


                    flag=1;
                }
                else{
                    Context context=view.getContext();
                    //intent2=new Intent(context, Tab1TextAdapter.class);
                    //intent2.putExtra("name",nameText.getText());
                    //intent2.putExtra("num",numText.getText());
                    edit.setText("edit"); //버튼 text 바꾸기
                    //edit 저장 editable false
                    nameText.setFocusable(false);
                    nameText.setClickable(false);
                    numText.setFocusable(false);
                    numText.setClickable(false);
                    inputMethodManager.hideSoftInputFromWindow(nameText.getWindowToken(),0);
                    inputMethodManager.hideSoftInputFromWindow(numText.getWindowToken(),0);
                    flag=0;
                }
            }
        });
        nameText.setText(name);
        numText.setText(num);






    }

}
