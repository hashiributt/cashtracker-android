package com.example.cashtracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.nav_user_name);
        TextView navEmail = headerView.findViewById(R.id.nav_user_email);
        SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);
        navName.setText(prefs.getString("name", "User"));
        navEmail.setText(prefs.getString("email", "user@email.com"));

        LinearLayout headerProfile = headerView.findViewById(R.id.nav_header_profile);
        headerProfile.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .commit();
            drawer.closeDrawer(GravityCompat.START);
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashboardFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        } else if (id == R.id.nav_budgeting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BudgetFragment()).commit();
        } else if (id == R.id.nav_expenses) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExpensesFragment()).commit();
        } else if (id == R.id.nav_reports) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportsFragment()).commit();
        } else if (id == R.id.nav_stock_news) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StockNewsFragment()).commit();
        } else if (id == R.id.nav_spending_tracker) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpendingTrackerFragment()).commit();
        } else if (id == R.id.nav_help) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HelpFragment()).commit();
        } else if (id == R.id.nav_reminders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RemindersFragment()).commit();
        } else if (id == R.id.nav_money_transfer) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MoneyTransferFragment()).commit();
        } else if (id == R.id.nav_spending_analyzer) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpendingAnalyzerFragment()).commit();
        } else if (id == R.id.nav_recommendations) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecommendationsFragment()).commit();
        } else if (id == R.id.nav_currency_converter) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CurrencyConverterFragment()).commit();
        } else if (id == R.id.nav_goal_setter) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GoalFragment()).commit();
        } else if (id == R.id.nav_export_report) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExportReportFragment()).commit();
        } else if (id == R.id.nav_subscription_tracker) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubscriptionTrackerFragment()).commit();
        } else if (id == R.id.nav_receipt_scanner) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReceiptScannerFragment()).commit();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
