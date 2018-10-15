package com.example.s3713532.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class Calculator extends Activity {

    private double value = Double.NaN;
    private double sum = Double.NaN;
    private boolean stat = true;

    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';

    private char CURRENT_ACTION = '0';

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calculator);

        editText = findViewById(R.id.editText);
        editText.setTypeface(Typeface.MONOSPACE);

        Button buttonZero = findViewById(R.id.buttonZero);
        Button buttonOne = findViewById(R.id.buttonOne);
        Button buttonTwo = findViewById(R.id.buttonTwo);
        Button buttonThree = findViewById(R.id.buttonThree);
        Button buttonFour = findViewById(R.id.buttonFour);
        Button buttonFive = findViewById(R.id.buttonFive);
        Button buttonSix = findViewById(R.id.buttonSix);
        Button buttonSeven = findViewById(R.id.buttonSeven);
        Button buttonEight = findViewById(R.id.buttonEight);
        Button buttonNine = findViewById(R.id.buttonNine);


        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);

        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonEqual = findViewById(R.id.buttonEqual);
        Button buttonClear = findViewById(R.id.buttonClear);

        Button buttonC2M = findViewById(R.id.buttonC2M);
        Button buttonC2T = findViewById(R.id.buttonC2T);

        buttonC2M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Calculator.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonC2T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Calculator.this, ToDo.class);
                startActivity(intent);
            }
        });


        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "0");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "1");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "2");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "3");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "4");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "5");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "6");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "7");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "8");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }
                editText.setText(editText.getText() + "9");
                editText.setSelection(editText.getText().length());
                stat = false;
            }
        });

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat) {
                    editText.setText(null);
                }

                if (!editText.getText().toString().contains(".")) {
                    editText.setText(editText.getText() + ".");
                    editText.setSelection(editText.getText().length());
                }

                stat = false;
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stat = true;
                calculate();
                CURRENT_ACTION = ADDITION;
            }
        });

        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stat = true;
                calculate();
                CURRENT_ACTION = SUBTRACTION;
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stat = true;
                calculate();
                CURRENT_ACTION = MULTIPLICATION;
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stat = true;
                calculate();
                CURRENT_ACTION = DIVISION;
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stat = true;
                calculate();
                sum = Double.NaN;
                value = Double.NaN;
                CURRENT_ACTION = '0';
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stat = true;
                sum = Double.NaN;
                value = Double.NaN;
                CURRENT_ACTION = '0';
                editText.setText(null);
            }
        });
    }

    private void calculate() {
        if (!Double.isNaN(sum)) {
            value = Double.parseDouble(editText.getText().toString());

            if (CURRENT_ACTION == ADDITION) {
                sum += value;
            } else if (CURRENT_ACTION == SUBTRACTION) {
                sum -= value;
            } else if (CURRENT_ACTION == MULTIPLICATION) {
                sum *= value;
            } else if (CURRENT_ACTION == DIVISION) {
                sum /= value;
            }

    } else {

                try {
                    sum = Double.parseDouble(editText.getText().toString());
                } catch (Exception e) {
                    System.out.println("Error occurred");
                }
        }
        editText.setText(Double.toString(sum));
    }

}
