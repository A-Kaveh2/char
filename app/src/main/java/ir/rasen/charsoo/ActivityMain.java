package ir.rasen.charsoo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(ActivityMain.this, ActivityTest.class);

        (findViewById(R.id.button_adapter_base)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("type", "button_adapter_base");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_business_post_adapter_comment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_business_post_adapter_comment");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_post_adapter_comment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_post_adapter_comment");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_adapter_comment_notification)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_adapter_comment_notification");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_adapter_user_reviews)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_adapter_user_reviews");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_dialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_dialog");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_popup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_popup");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_adapter_business_reviews)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_adapter_business_reviews");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_friends)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_friends");
                startActivity(intent);
            }
        });

        (findViewById(R.id.button_followers)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_followers");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_following_businesses)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_following_businesses");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_user_search_result)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_user_search_result");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_business_search_result)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_business_search_result");
                startActivity(intent);
            }
        });
        (findViewById(R.id.button_friends_request)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_friends_request");
                startActivity(intent);
            }
        });(findViewById(R.id.button_time_line_posts)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", "button_time_line_posts");
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
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
}
