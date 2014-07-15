package com.example.owl;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.example.owl.utils.Utils;
import com.facebook.Session;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class NaviActivity extends FragmentActivity implements
        TabHost.OnTabChangeListener {

    private TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    private TabInfo mLastTab = null;
    Context context;

    private class TabInfo {
        private String _tag;
        private int _labelId;
        private int _drawableId;
        @SuppressWarnings("rawtypes")
        private Class _class;
        private Bundle _args;
        private Fragment _fragment;

        @SuppressWarnings("rawtypes")
        TabInfo(int labelID, int drawableId, Class cl, Bundle args) {
            this._tag = "tab" + labelID;
            this._labelId = labelID;
            this._drawableId = drawableId;
            this._class = cl;
            this._args = args;
        }

        public int get_labelId() {
            return _labelId;
        }

        public int get_drawableId() {
            return _drawableId;
        }

        @SuppressWarnings("rawtypes")
        public Class get_class() {
            return _class;
        }

        public Fragment get_fragment() {
            return _fragment;
        }

        public String get_tag() {
            return _tag;
        }

    }

    class TabFactory implements TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         *
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup vg = (ViewGroup) findViewById(R.id.main_root);
        getActionBar().hide();
        
        context = this;
        
     // Fetch Facebook user info if the session is active
     		Session session = ParseFacebookUtils.getSession();
     		if (session != null && session.isOpened()) {
     			
     			//request some data, do something with facebook: Example, fill layout with info from facebook
     			//makeMeRequest();
     		}


        // Step 2: Setup TabHost
        initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            // set the tab as per the saved state
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }

        Utils.setFontAllView(vg);
    }
    
	@Override
	public void onResume() {
		super.onResume();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
//			updateViewsWithProfileInfo();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			showLoginActivity();
		}
	}
	
	private void showLoginActivity() {
		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
	}

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab
        // selected
        super.onSaveInstanceState(outState);
    }

    /**
     * Step 2: Setup TabHost
     */
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.getTabWidget().setDividerDrawable(null);

        addTab(R.string.lbl_title, R.drawable.ico_list, SwipeActivity.class,
                args);
        addTab(R.string.lbl_user, R.drawable.ico_person, UserActivity.class,
                args);

        // Default to first tab
        mTabHost.setCurrentTabByTag("tab" + R.string.lbl_title);
        this.onTabChanged("tab" + R.string.lbl_title);

        mTabHost.setOnTabChangedListener(this);
    }

    private void addTab(int labelID, int drawableId, Class cl, Bundle args) {

        TabInfo tabInfo = null;
        tabInfo = new TabInfo(labelID, drawableId, cl, args);
        this.mapTabInfo.put(tabInfo.get_tag(), tabInfo);

        TabHost.TabSpec spec = mTabHost.newTabSpec(tabInfo._tag);

        View tabIndicator = LayoutInflater.from(this).inflate(
                R.layout.tab_indicator,
                (ViewGroup) findViewById(android.R.id.tabs), false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(tabInfo.get_labelId());
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(tabInfo.get_drawableId());

        spec.setIndicator(tabIndicator);

        // Attach a Tab view factory to the spec
        spec.setContent(this.new TabFactory(this));
        String tag = spec.getTag();

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state. If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo._fragment = this.getSupportFragmentManager().findFragmentByTag(
                tag);
        if (tabInfo._fragment != null && !tabInfo._fragment.isDetached()) {
            FragmentTransaction ft = this.getSupportFragmentManager()
                    .beginTransaction();
            ft.detach(tabInfo._fragment);
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }

        mTabHost.addTab(spec);
    }

    public void onTabChanged(String tag) {
        TabInfo newTab = this.mapTabInfo.get(tag);
        if (mLastTab != newTab) {
            FragmentTransaction ft = this.getSupportFragmentManager()
                    .beginTransaction();
            if (mLastTab != null) {
                if (mLastTab._fragment != null) {
                    ft.detach(mLastTab._fragment);
                }
            }
            if (newTab != null) {
                if (newTab.get_fragment() == null) {
                    newTab._fragment = Fragment.instantiate(this, newTab
                            .get_class().getName(), newTab._args);
                    ft.add(R.id.realtabcontent, newTab.get_fragment(),
                            newTab._tag);
                } else {
                    ft.attach(newTab.get_fragment());
                }
            }

            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }
    }
}
