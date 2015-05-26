package com.bam.darkhouseextreme.app.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.fragments.RoomFragment;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Chobii on 28/04/15.
 */
public class GameActivity extends FragmentActivity {

    private RoomFragment fragment;
    private MediaPlayer mediaPlayer;
    private Toast toast;
    private TextView toastText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Utilities.setBooleanValues();
        fragment = new RoomFragment();




        setButtonsForRoom02();
        setButtonsForRoom01();
        setButtonsForRoom11();
        setButtonsForRoom12();
        setButtonsForRoom13();
        setButtonsForRoom20();
        setButtonsForRoom21();
        setButtonsForRoom22();
        setButtonsForRoom23();
        setButtonsForRoom32();
        setButtonsForRoom33();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.gamelayout, fragment, "room")
                    .commit();
        }

        final Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/MISFITS_.TTF");

        LayoutInflater inflater = getLayoutInflater();
        View toastView = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_root));

        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200);
        toast.setView(toastView);

        toastText = (TextView) toastView.findViewById(R.id.toast_text);
        Utilities.setFontForView(toastText, font);


    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        fragment.nullifyAndRemoveButtonsFromParent();
        FragmentTransaction transaction =
                StartScreenActivity.activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.startscreenlayout,
                StartScreenActivity.activity
                        .getSupportFragmentManager()
                        .findFragmentByTag("startScreen")
        );

        transaction.commitAllowingStateLoss();
        finish();
    }

    private void setButtonsForRoom02() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

        doorRight.setBackgroundResource(R.drawable.placeholder);
        doorDown.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorRight);
        buttons.add(doorDown);

        Button note = new Button(getApplicationContext());
        note.setTag("paper");
        note.setBackgroundResource(R.drawable.placeholder);
        buttons.add(note);

        note.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                        intent.putExtra("image", R.drawable.note_dialog);
                        startActivity(intent);
                    }
                }
        );


        if (!SaveUtility.alreadyHasItem("1")) {
            Button ductTape = new Button(getApplicationContext());
            ductTape.setTag("ductTape");
            ductTape.setBackgroundResource(R.drawable.duct_tape);
            buttons.add(ductTape);


            ductTape.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toastText.setText("Duct tape? Might be useful.");
                            toast.show();
                            SaveUtility.saveItemToCharacter("1");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("02").remove(v);
                        }
                    }
            );
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 1);
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utilities.room02) {
                            fragment.isRoom(1, 2);
                        } else {
                            Toast.makeText(getApplicationContext(), "The wall seem to crumble.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("02", buttons);
    }

    private void setButtonsForRoom01() {

        List<Button> buttons = new ArrayList<>();
        Button doorUp = new Button(getApplicationContext());
        Button doorRight2 = new Button(getApplicationContext());
        final Button key = new Button(getApplicationContext());
        Button blood = new Button(getApplicationContext());
        doorRight2.setBackgroundResource(R.drawable.placeholder);
        doorUp.setBackgroundResource(R.drawable.placeholder);
        blood.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorUp);
        buttons.add(doorRight2);
        buttons.add(blood);


        if (!SaveUtility.alreadyHasItem("2")) {
            key.setBackgroundResource(R.drawable.key);
            if (Utilities.doorOpened("01") == 0) {
                key.setVisibility(View.GONE);
            }
            buttons.add(key);

            key.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SaveUtility.saveItemToCharacter("2");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("01").remove(v);
                        }
                    }
            );
        }

        if (Utilities.doorOpened("01") == 0) {
            Button carpet = new Button(getApplicationContext());
            carpet.setBackgroundResource(R.drawable.placeholder);
            buttons.add(carpet);

            carpet.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(getApplicationContext(), "You stumble on the carpet, flipping the side over.", Toast.LENGTH_SHORT).show();
                            toastText.setText("You stumble on the carpet, flipping the side over.");
                            toast.show();
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("01").remove(v);
                            SaveUtility.player.setRoom01(true);
                            Utilities.room01 = true;
                            fragment.eventTriggeredSwap("01");
                            key.setVisibility(View.VISIBLE);

                        }
                    }
            );
        }


        blood.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toastText.setText("looks like someone hit their head hard on the table");
                        toast.show();
                    }
                }
        );

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 2);
                    }
                }
        );

        doorRight2.setOnClickListener(
                new View.OnClickListener() {
                    int numOfClicks = 0;

                    @Override
                    public void onClick(View v) {

                        if (numOfClicks == 1 && SaveUtility.player.isRoom01()) {
                            fragment.isRoom(1, 1);
                        } else if (SaveUtility.alreadyHasItem("2") && numOfClicks == 0) {
                            SaveUtility.removeItemFromCharacter("2");
//                            Toast.makeText(getApplicationContext(), "You unlocked the door!", Toast.LENGTH_SHORT).show();
                            toastText.setText("You unclocked the door!");
                            toast.show();
                            SaveUtility.player.setRoom01a(true);
                            Utilities.room01a = true;
                            fragment.eventTriggeredSwap("01a");
                            numOfClicks++;
                        } else {
//                            Toast.makeText(getApplicationContext(), "Door is locked", Toast.LENGTH_SHORT).show();
                            toastText.setText("Door is locked!");
                            toast.show();
                        }
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setPadding(0, 0, 0, 0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("01", buttons);
    }

    private void setButtonsForRoom11() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());
        Button clock = new Button(getApplicationContext());
        Button gasline = new Button(getApplicationContext());


        doorRight.setBackgroundResource(R.drawable.placeholder);
        doorLeft.setBackgroundResource(R.drawable.placeholder);
        clock.setBackgroundResource(R.drawable.clock_no_hands);
        gasline.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorLeft);
        buttons.add(doorRight);
        buttons.add(clock);
        buttons.add(gasline);

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SaveUtility.alreadyHasItem("7") && SaveUtility.alreadyHasItem("8")) {
                    Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                    intent.putExtra("image", R.drawable.complete_clock);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "This clock makes no sense! Or does it?", Toast.LENGTH_SHORT).show();
                } else if (!SaveUtility.alreadyHasItem("7") && SaveUtility.alreadyHasItem("8")) {
                    Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                    intent.putExtra("image", R.drawable.clock_with_minute_hand);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "This clock is still incomplete, it needs an hour hand", Toast.LENGTH_SHORT).show();
                } else if (SaveUtility.alreadyHasItem("7") && !SaveUtility.alreadyHasItem("8")) {
                    Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                    intent.putExtra("image", R.drawable.clock_with_hour_hand);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), "This clock is still incomplete, it needs an minute hand", Toast.LENGTH_SHORT).show();
                    toastText.setText("This clock is still incomplete, it needs a minute hand");
                    toast.show();
                } else {
//                    Toast.makeText(getApplicationContext(), "This clock is incomplete", Toast.LENGTH_SHORT).show();
                    toastText.setText("This clock is incomplete");
                    toast.show();
                }
            }
        });

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!SaveUtility.alreadyHasItem("13")) {
//                            Toast.makeText(getApplicationContext(), "Door is locked!", Toast.LENGTH_SHORT).show();
                            toastText.setText("Door is locked!");
                            toast.show();
                        } else if (!Utilities.room11a) {
                            SaveUtility.removeItemFromCharacter("13");
//                            Toast.makeText(getApplicationContext(), "You opened the door!!", Toast.LENGTH_SHORT).show();
                            toastText.setText("You opened the door!");
                            toast.show();
                            Utilities.room11a = true;
                            SaveUtility.player.setRoom11a(true);
                            fragment.eventTriggeredSwap("11a");
                        } else {
                            fragment.isRoom(0, 1);
                            // I like squirrels very much
                        }
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Utilities.room11) {
//                            Toast.makeText(getApplicationContext(), "Door is locked!", Toast.LENGTH_SHORT).show();
                            toastText.setText("Door is locked!");
                            toast.show();
                        } else {
                            fragment.isRoom(2, 1);
                        }
                    }
                }
        );

        gasline.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.fixGasLeak();
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("11", buttons);

    }

    private void setButtonsForRoom21b() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button doorUp = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());
        Button table = new Button(getApplicationContext());

        doorDown.setBackgroundResource(R.drawable.placeholder);
        doorLeft.setBackgroundResource(R.drawable.placeholder);
        doorUp.setBackgroundResource(R.drawable.placeholder);
        table.setBackgroundResource(R.drawable.doorblockade);

        doorDown.setTag("door");

        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorUp);
        buttons.add(table);

        if (!SaveUtility.alreadyHasItem("8")) {

            Button minuteHand = new Button(getApplicationContext());

            minuteHand.setBackgroundResource(R.drawable.minute_hand);
            buttons.add(minuteHand);

            minuteHand.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "A minute hand. Might fit the clock.", Toast.LENGTH_SHORT).show();
                            SaveUtility.saveItemToCharacter("8");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("21").remove(v);
                        }
                    }
            );
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 0);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 1);
                    }
                }
        );

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 2);
                    }
                }
        );

        table.setOnClickListener(
                new View.OnClickListener() {
                    int clickCount = 0;

                    @Override
                    public void onClick(View v) {
                        if (clickCount == 0) {
//                            Toast.makeText(getApplicationContext(), "It sure was heavy", Toast.LENGTH_SHORT).show();
                            toastText.setText("It sure was heavy");
                            toast.show();
                        }
                        clickCount++;
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("21", buttons);

    }

    private void setButtonsForRoom21a() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button table = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

        doorDown.setBackgroundResource(R.drawable.placeholder);
        doorLeft.setBackgroundResource(R.drawable.placeholder);


        table.setBackgroundResource(R.drawable.doorblockade);

        table.setTag("table");

        buttons.add(table);
        buttons.add(doorDown);
        buttons.add(doorLeft);

        if (!SaveUtility.alreadyHasItem("8")) {

            Button minuteHand = new Button(getApplicationContext());

            minuteHand.setBackgroundResource(R.drawable.minute_hand);
            buttons.add(minuteHand);

            minuteHand.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "A minute hand. Might fit the clock.", Toast.LENGTH_SHORT).show();
                            SaveUtility.saveItemToCharacter("8");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("21").remove(v);
                        }
                    }
            );
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 0);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 1);
                    }
                }
        );

        table.setOnClickListener(
                new View.OnClickListener() {
                    int clickCount = 0;

                    @Override
                    public void onClick(View v) {
                        if (clickCount == 5) {
                            setButtonsForRoom21b();
                            fragment.eventTriggeredSwap("21");
                        }
                        if (clickCount < 5) {
                            fragment.moveTable();
                            clickCount++;
                        }
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("21", buttons);

    }

    private void setButtonsForRoom21() {

        List<Button> buttons = new ArrayList<>();

        if (SaveUtility.player.isRoom21()) {
            setButtonsForRoom21a();
        } else {
            Button light = new Button(getApplicationContext());

            light.setBackgroundResource(R.drawable.placeholder);

            buttons.add(light);

            light.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SaveUtility.player.setRoom21(true);
                            Utilities.room21 = true;
                            setButtonsForRoom21a();
                            fragment.eventTriggeredSwap("21");
                        }
                    }
            );

            for (Button b : buttons) {
                b.setMinHeight(0);
                b.setMinimumHeight(0);
                b.setMinWidth(0);
                b.setMinimumWidth(0);
                b.setAlpha(1.0f);
            }

            Utilities.setButtonsForRooms("21", buttons);
        }
    }

    private void setButtonsForRoom20() {

        List<Button> buttons = new ArrayList<>();

        Button doorUp = new Button(getApplicationContext());
        final Button toilet = new Button(getApplicationContext());
        final Button hourHand = new Button(getApplicationContext());
        final Button water = new Button(getApplicationContext());
        water.setBackgroundResource(R.drawable.placeholder);

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!SaveUtility.alreadyHasItem("12")) {
                    if (!SaveUtility.alreadyHasItem("11")) {
                        Toast.makeText(getApplicationContext(), "Water has leaked onto the floor.", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveUtility.saveItemToCharacter("12");
                        Toast.makeText(getApplicationContext(), "You filled the bucket with water", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Somebody should fix this leak.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        toilet.setBackgroundResource(R.drawable.placeholder);
        doorUp.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorUp);


        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 1);
                    }
                }
        );

        if (Utilities.doorOpened("20") == 0) {

            toilet.setTag("toilet");

            toilet.setOnClickListener(
                    new View.OnClickListener() {
                        int clickCount = 0;

                        @Override
                        public void onClick(View v) {
                            switch (clickCount) {
                                case 0:
                                    Toast.makeText(getApplicationContext(), "There might be something useful in there.", Toast.LENGTH_SHORT).show();
                                    clickCount++;
                                    break;
                                case 1:
                                    toastText.setText("It's really disgusting though.");
                                    toast.show();
                                    clickCount++;
                                    break;
                                case 2:
                                    toastText.setText("Guess it's all or nothing!");
                                    toast.show();
                                    clickCount++;
                                    break;
                                case 3:
                                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                                    layout.removeView(v);
                                    Utilities.buttonsForRooms.get("20").remove(v);
                                    hourHand.setVisibility(View.VISIBLE);
                                    Utilities.room20 = true;
                                    SaveUtility.player.setRoom20(true);
                                    fragment.eventTriggeredSwap("20");
                            }
                        }
                    }
            );

            buttons.add(toilet);
        }

        if (!SaveUtility.alreadyHasItem("7")) {

            hourHand.setBackgroundResource(R.drawable.hour_hand);
            if (Utilities.doorOpened("20") == 0) {
                hourHand.setVisibility(View.INVISIBLE);
            }
            hourHand.setTag("hourHand");
            buttons.add(hourHand);

            hourHand.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Oh! An hour hand. Might fit the clock.", Toast.LENGTH_SHORT).show();
                            SaveUtility.saveItemToCharacter("7");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("20").remove(v);
                        }
                    }
            );
        }
        buttons.add(water);

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("20", buttons);

    }

    private void setButtonsForRoom22() {

        List<Button> buttons = new ArrayList<>();

        Button doorDown = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());
        Button doorRight = new Button(getApplicationContext());
        Button doorUp = new Button(getApplicationContext());
        final Button book = new Button(getApplicationContext());

        doorUp.setBackgroundResource(R.drawable.placeholder);
        doorDown.setBackgroundResource(R.drawable.placeholder);
        doorLeft.setBackgroundResource(R.drawable.placeholder);
        doorRight.setBackgroundResource(R.drawable.placeholder);
        book.setBackgroundResource(R.drawable.placeholder);


        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorRight);
        buttons.add(doorUp);
        buttons.add(book);


        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 1);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!SaveUtility.alreadyHasItem("13")) {
//                            Toast.makeText(getApplicationContext(), "Door is locked!", Toast.LENGTH_SHORT).show();
                            toastText.setText("Door is locked!");
                            toast.show();
                        } else {
                            SaveUtility.player.setRoom12(true);
                            SaveUtility.player.setDead(true);
                            Utilities.room12 = true;
                            fragment.isRoom(1, 2);

                            Handler handler = new Handler();

                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Random rn = new Random();
                                            int randomNumber = rn.nextInt(2) + 1;
                                            MediaPlayer mediaPlayer;
                                            switch (randomNumber) {
                                                case 1:
                                                    mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.death1);
                                                    mediaPlayer.setVolume(100, 100);
                                                    mediaPlayer.start();
                                                    break;
                                                case 2:
                                                    mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.death2);
                                                    mediaPlayer.setVolume(100, 100);
                                                    mediaPlayer.start();
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }
                                    , 1000);

                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            onBackPressed();
                                        }
                                    },
                                    4000);
                        }
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if(!SaveUtility.alreadyHasItem("13")){
                        //TODO: Fix so that condition below works.
                        if (!Utilities.room22) {
//                            Toast.makeText(getApplicationContext(), "Door can only be opened from the other side!", Toast.LENGTH_SHORT).show();
                            toastText.setText("Door can only be opened from the other side!");
                            toast.show();
                        } else {
                            fragment.isRoom(3, 2);
                        }
                    }
                }
        );

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 3);
                    }
                }
        );

        book.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                        intent.putExtra("image", R.drawable.book_dialog);
                        startActivity(intent);
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("22", buttons);
    }

    private void setButtonsForRoom12() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());
        Button stairs = new Button(getApplicationContext());

        doorLeft.setBackgroundResource(R.drawable.placeholder);
        doorRight.setBackgroundResource(R.drawable.placeholder);
        stairs.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorRight);
        buttons.add(doorLeft);
        buttons.add(stairs);

        stairs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                        intent.putExtra("image", R.drawable.dialog_1);
                        startActivity(intent);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        }, 7000);

                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 2);
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setPadding(0, 0, 0, 0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("12", buttons);

    }

    private void setButtonsForRoom13() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button lever = new Button(getApplicationContext());

        lever.setBackgroundResource(R.drawable.placeholder);
        doorRight.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorRight);
        buttons.add(lever);
        lever.setOnClickListener(
                new View.OnClickListener() {
                    int clickCount = 0;

                    @Override
                    public void onClick(View v) {
                        if (!SaveUtility.alreadyHasItem("9")) {
                            switch (clickCount) {
                                case 0:
//                                    Toast.makeText(getApplicationContext(), "Looks like a lever", Toast.LENGTH_SHORT).show();
                                    toastText.setText("Looks like a lever of some kind");
                                    toast.show();
                                    clickCount++;
                                    break;
                                case 1:
//                                    Toast.makeText(getApplicationContext(), "There should be something in this house i can use", Toast.LENGTH_SHORT).show();
                                    toastText.setText("There should be something in this house i can use");
                                    toast.show();
                                    break;
                            }

                        } else if (!Utilities.room13) {
                            SaveUtility.removeItemFromCharacter("9");
                            Utilities.room13 = true;
                            SaveUtility.player.setRoom13(true);
//                            Toast.makeText(getApplicationContext(), "The handle fit perfectly", Toast.LENGTH_SHORT).show();
                            toastText.setText("The handle fit perfectly!");
                            toast.show();
                            fragment.eventTriggeredSwap("13");
                        } else {
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.room02 = true;
                            Utilities.room13a = true;
                            SaveUtility.player.setRoom02(true);
                            SaveUtility.player.setRoom13a(true);
                            fragment.eventTriggeredSwap("13a");
                            Utilities.buttonsForRooms.get("13").remove(v);
//                            Toast.makeText(getApplicationContext(), "You hear a loud rumbling nearby", Toast.LENGTH_SHORT).show();
                            toastText.setText("You hear a loud rumbling nearby");
                            toast.show();
                        }
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 3);
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("13", buttons);

    }

    private void setButtonsForRoom23() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button doorRight = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());
        Button painting = new Button(getApplicationContext());

        doorLeft.setBackgroundResource(R.drawable.placeholder);
        doorDown.setBackgroundResource(R.drawable.placeholder);
        doorRight.setBackgroundResource(R.drawable.placeholder);
        painting.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorRight);
        buttons.add(painting);

        painting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 2);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 3);
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(3, 3);
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("23", buttons);
    }

    private void setButtonsForRoom33() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

        doorLeft.setBackgroundResource(R.drawable.placeholder);
        doorDown.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorDown);
        buttons.add(doorLeft);

        if (!SaveUtility.alreadyHasItem("11")) {

            Button bucket = new Button(getApplicationContext());

            buttons.add(bucket);

            bucket.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("33").remove(v);
                            SaveUtility.saveItemToCharacter("11");
                        }
                    }
            );
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    int numOfClicks = 0;

                    @Override
                    public void onClick(View v) {

                        if (!SaveUtility.player.isRoom33()) {
                            fragment.openLockFragment();
                        } else {
                            if (SaveUtility.player.isRoom33() && numOfClicks == 0) {
                                fragment.eventTriggeredSwap("33");
                                numOfClicks++;
                            } else {
                                fragment.isRoom(3, 2);
                            }
                        }
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 3);
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("33", buttons);
    }

    private void setButtonsForRoom32() {

        List<Button> buttons = new ArrayList<>();

        Button doorUp = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());

        doorLeft.setBackgroundResource(R.drawable.placeholder);
        doorUp.setBackgroundResource(R.drawable.placeholder);

        buttons.add(doorLeft);
        buttons.add(doorUp);

        if (!SaveUtility.alreadyHasItem("13")) {
            Button masterKey = new Button(getApplicationContext());
            masterKey.setBackgroundResource(R.drawable.master_key);
            buttons.add(masterKey);

            masterKey.setBackgroundResource(R.drawable.master_key);

            masterKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                    layout.removeView(v);
                    Utilities.buttonsForRooms.get("32").remove(v);
                    SaveUtility.saveItemToCharacter("13");
                }
            });
        }

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    int numOfClicks = 0;

                    @Override
                    public void onClick(View v) {
                        if (Utilities.room32) {
                            fragment.isRoom(2, 2);
                        } else if (SaveUtility.alreadyHasItem("13") && numOfClicks == 0 && !Utilities.room32) {
//                            Toast.makeText(getApplicationContext(), "You unlocked the door!", Toast.LENGTH_SHORT).show();
                            toastText.setText("You unlocked the door!");
                            toast.show();
                            SaveUtility.player.setRoom32(true);
                            Utilities.room32 = true;
                            Utilities.room22 = true;
                            SaveUtility.player.setRoom22(true);
                            fragment.eventTriggeredSwap("32");
                            numOfClicks++;
                        } else {
//                            Toast.makeText(getApplicationContext(), "Door is locked", Toast.LENGTH_SHORT).show();
                            toastText.setText("Door is locked!");
                            toast.show();
                        }
                    }
                }
        );

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(3, 3);
                    }
                }
        );

        for (Button b : buttons) {
            b.setMinHeight(0);
            b.setMinimumHeight(0);
            b.setPadding(0, 0, 0, 0);
            b.setMinWidth(0);
            b.setMinimumWidth(0);
            b.setAlpha(1.0f);
        }

        if (!SaveUtility.alreadyHasItem("9")) {
            Button handle = new Button(getApplicationContext());
            handle.setBackgroundResource(R.drawable.lever_handle);
            handle.setAlpha(0f);

            buttons.add(handle);

            handle.setOnClickListener(
                    new View.OnClickListener() {
                        int numberOfClicks = 0;

                        @Override
                        public void onClick(View v) {
                            if (numberOfClicks == 0) {
                                v.setAlpha(1.0f);
//                                Toast.makeText(getApplicationContext(), "You pulled something out from under the bed!", Toast.LENGTH_SHORT).show();
                                toastText.setText("You pulled something out from under the bed");
                                toast.show();
                                numberOfClicks++;
                            } else {
//                                Toast.makeText(getApplicationContext(), "It's a lever! Where might this be used?", Toast.LENGTH_SHORT).show();
                                toastText.setText("It's a handle of some sort! Where might this go?");
                                toast.show();
                                RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                                layout.removeView(v);
                                Utilities.buttonsForRooms.get("32").remove(v);
                                SaveUtility.saveItemToCharacter("9");
                            }
                        }
                    }
            );
        }

        Utilities.setButtonsForRooms("32", buttons);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.game_music);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
    }

    public Toast getToast() {
        return toast;
    }

    public TextView getToastText() {
        return toastText;
    }
}
