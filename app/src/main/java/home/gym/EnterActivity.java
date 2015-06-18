package home.gym;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 * Created by greg on 16.06.15.
 */
public class EnterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
    public void enter(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
