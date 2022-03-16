
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import java.util.List;

/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static final int ITEMS_COUNT = 12;
    private static final int FAVORITE_ITEMS_COUNT = 0;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void neighbourClick_shouldLaunch_DetailActivity(){
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(ViewMatchers.withId((R.id.activity_detail_container)))
                .check(matches(isDisplayed()));

        //autre solution : verifier que c'est le bon intent
    }

    @Test
    public void detailActivity_shouldDisplay_theRightName(){
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,click()));

        String name = DI.getNewInstanceApiService().getNeighbours().get(1).getName();

        onView(ViewMatchers.withId((R.id.neighbour_name)))
                .check(matches(withText(name)));

    }

    @Test
    public void favoritesTab_shouldDisplay_onlyFavoriteNeighbours(){

        //check the initial value of the two lists :
        onView(ViewMatchers.withId(R.id.list_favorite)).check(withItemCount(FAVORITE_ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.list_favorite)).check(withItemCount(ITEMS_COUNT));

        // perform click :
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(ViewMatchers.withId(R.id.favorite_button)).perform(click());
        onView(ViewMatchers.withId(R.id.back_button)).perform(click());

        //check that favorite list has incremented:
        onView(ViewMatchers.withId(R.id.list_favorite)).check(withItemCount(FAVORITE_ITEMS_COUNT+1));

        // check that neighbour list has not incremented :
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));

    }




}