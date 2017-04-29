
package com.company;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;


/**
 * Created by julianareider on 4/17/17.
 */

public class TripTest {
    private Plane tempPlane;
    private Trip tempTrip;

    /**
     * Tests the loadAirplane() method and its helper methods
     * @result expected output matches the string generated by the method call
     * @throws Exception if cargo item has weight or value of 0
     */
    @Test
    public void loadAirplane() throws Exception {
        CargoItem firstItem = new CargoItem(1, "firstItem", 1);
        CargoItem secondItem = new CargoItem(3, "secondItem", 4);
        CargoItem thirdItem = new CargoItem(4, "thirdItem", 5);
        CargoItem fourthItem = new CargoItem(5, "fourthItem", 7);
        CargoItemList itemList = new CargoItemList();
        itemList.addCargoItemToList(firstItem);
        itemList.addCargoItemToList(secondItem);
        itemList.addCargoItemToList(thirdItem);
        itemList.addCargoItemToList(fourthItem);
        tempPlane = new Plane("This kind of plane", 7);
        tempTrip = new Trip (tempPlane);
        //check for proper return string
        String expectedOutput0 = "Airplane was loaded. \n\n" +
                "Here are the details of your trip: \n\n" +
                "Type of aircraft: This kind of plane\n" +
                "Weight Limit: 7 ounces \n" +
                "Bring these items: \n" +
                "\tItem: thirdItem, Weight: 4, Value: 5\n" +
                "\tItem: secondItem, Weight: 3, Value: 4\n" +
                "Total value of these items is: 9\n" +
                "Leave behind these items: \n" +
                "\tItem: firstItem, Weight: 1, Value: 1\n"+
                "\tItem: fourthItem, Weight: 5, Value: 7\n";

        String expectedOutput1 = "Airplane was loaded. \n\n" +
                "Here are the details of your trip: \n\n" +
                "Type of aircraft: This kind of plane\n" +
                "Weight Limit: 7 ounces \n" +
                "Bring these items: \n" +
                "\tItem: thirdItem, Weight: 4, Value: 5\n" +
                "\tItem: secondItem, Weight: 3, Value: 4\n" +
                "Total value of these items is: 9\n" +
                "Leave behind these items: \n" +
                "\tItem: fourthItem, Weight: 5, Value: 7\n" +
                "\tItem: firstItem, Weight: 1, Value: 1\n";
        String actualString = tempTrip.loadAirplane(itemList);

        assertTrue(actualString.equals(expectedOutput0) || actualString.equals(expectedOutput1));
    }

    @Test
    public void bringAllItemsWhenTwoItemsAreIdentical() throws Exception {
        //test that proper optimal list stored when 1) two identical items exist on the list 2)All items on potential list should be included
        CargoItemList cargoList = new CargoItemList();
        cargoList.addCargoItemToList(new CargoItem(2, "b", 2));
        cargoList.addCargoItemToList(new CargoItem(2, "a", 2));
        Plane aPlane = new Plane("This kind of plane", 4); //weight lim = 4
        Trip aTrip = new Trip (aPlane);
        aTrip.loadAirplane(cargoList);
        CargoItemList expectedCargoItemList = new CargoItemList();
        expectedCargoItemList.addCargoItemToList(new CargoItem(2, "a", 2));
        expectedCargoItemList.addCargoItemToList(new CargoItem(2, "b", 2));
        assertEquals(expectedCargoItemList.toString(), aTrip.getPlaneForTrip().getItemsToTake().toString());
        assertEquals("", aTrip.getCargoListGenerator().getItemsLeftBehind().toString());

    }

    /**
     * Tests that all items are brought when appropriate in various situations
     * Also tests that the method stores the optimal solution when all items on potentialItemList should be included
     * @result expected output matches the string generated by the method call
     * @throws Exception if cargo item has weight or value of 0
     */
    @Test
    public void bringAllItems() throws Exception {
        CargoItem firstItem = new CargoItem(1, "firstItem", 1);  // test that proper optimal list stored when 1) only one item on potential list
        CargoItemList cList = new CargoItemList();
        cList.addCargoItemToList(firstItem);
        tempPlane = new Plane("This kind of plane", 2);
        tempTrip = new Trip (tempPlane);
        tempTrip.loadAirplane(cList);
        assertEquals(cList.toString(),tempTrip.getPlaneForTrip().getItemsToTake().toString());
        assertEquals("", tempTrip.getCargoListGenerator().getItemsLeftBehind().toString());

        // test that proper optimal list stored when 1) Many items are on list (8) 2)All items on potential list should be included
        CargoItem c1 = new CargoItem(2,"c1",2);
        CargoItem c2 = new CargoItem(1,"c2",1);
        CargoItem c3 = new CargoItem(3,"c3",2);
        CargoItem c4 = new CargoItem(2,"c4",3);
        CargoItem c5 = new CargoItem(4,"c5",4);
        CargoItem c6 = new CargoItem(4,"c6",1);
        CargoItem c7 = new CargoItem(3,"c7",1);
        CargoItem c8 = new CargoItem(3,"c8",3);

        CargoItemList julianaItems = new CargoItemList();
        julianaItems.addCargoItemToList(c1);
        julianaItems.addCargoItemToList(c2);
        julianaItems.addCargoItemToList(c3);
        julianaItems.addCargoItemToList(c4);
        julianaItems.addCargoItemToList(c5);
        julianaItems.addCargoItemToList(c6);
        julianaItems.addCargoItemToList(c7);
        julianaItems.addCargoItemToList(c8);

        Plane bPlane = new Plane("Airbus", 30);
        Trip bTrip = new Trip(bPlane);
        bTrip.loadAirplane(julianaItems);

        CargoItemList expectedCargoItemList = new CargoItemList();
        expectedCargoItemList.addCargoItemToList(c8);
        expectedCargoItemList.addCargoItemToList(c7);
        expectedCargoItemList.addCargoItemToList(c6);
        expectedCargoItemList.addCargoItemToList(c5);
        expectedCargoItemList.addCargoItemToList(c4);
        expectedCargoItemList.addCargoItemToList(c3);
        expectedCargoItemList.addCargoItemToList(c2);
        expectedCargoItemList.addCargoItemToList(c1);

        assertEquals(expectedCargoItemList.toString(), bTrip.getPlaneForTrip().getItemsToTake().toString());
        assertEquals("", bTrip.getCargoListGenerator().getItemsLeftBehind().toString());
    }

    @Test
    public void noItemsOnPList() throws Exception {
        // E: test that proper optimal list stored when no items are in the potential list of items
        CargoItemList julianaItems = new CargoItemList();
        Plane largePlane = new Plane();
        largePlane.getItemsToTake().clearList();
        Trip fallTrip = new Trip(largePlane);
        fallTrip.loadAirplane(julianaItems);

        assertEquals("", fallTrip.getPlaneForTrip().getItemsToTake().toString());
        assertEquals("", fallTrip.getCargoListGenerator().getItemsLeftBehind().toString());
    }

    @Test
    public void allItemsLeftBehindManyItemsOnPlist() throws Exception {
        CargoItem c1 = new CargoItem(2,"c1",2);
        CargoItem c2 = new CargoItem(5,"c2",1);
        CargoItem c3 = new CargoItem(3,"c3",2);
        CargoItem c4 = new CargoItem(2,"c4",3);
        CargoItem c5 = new CargoItem(4,"c5",4);
        CargoItem c6 = new CargoItem(4,"c6",1);
        CargoItem c7 = new CargoItem(3,"c7",1);
        CargoItem c8 = new CargoItem(3,"c8",3);

        CargoItemList julianaItems = new CargoItemList();
        julianaItems.addCargoItemToList(c1);
        julianaItems.addCargoItemToList(c2);
        julianaItems.addCargoItemToList(c3);
        julianaItems.addCargoItemToList(c4);
        julianaItems.addCargoItemToList(c5);
        julianaItems.addCargoItemToList(c6);
        julianaItems.addCargoItemToList(c7);
        julianaItems.addCargoItemToList(c8);

        Plane bPlane = new Plane("Airbus", 1);
        Trip RomeTrip = new Trip(bPlane);
        RomeTrip.loadAirplane(julianaItems);

        CargoItemList expectedCargoItemList = new CargoItemList();
        expectedCargoItemList.addCargoItemToList(c8);
        expectedCargoItemList.addCargoItemToList(c7);
        expectedCargoItemList.addCargoItemToList(c6);
        expectedCargoItemList.addCargoItemToList(c5);
        expectedCargoItemList.addCargoItemToList(c4);
        expectedCargoItemList.addCargoItemToList(c3);
        expectedCargoItemList.addCargoItemToList(c2);
        expectedCargoItemList.addCargoItemToList(c1);

        HashSet<CargoItem> expectedItemsSet = new HashSet<>();
        HashSet<CargoItem> actualItemSet = new HashSet<>();

        expectedItemsSet.addAll(expectedCargoItemList.getItemArrayList());
        actualItemSet.addAll(RomeTrip.getCargoListGenerator().getItemsLeftBehind().getItemArrayList());

        assertEquals("", RomeTrip.getPlaneForTrip().getItemsToTake().toString());
        assertEquals(expectedItemsSet, actualItemSet);


    }

    //additional test: TODO Many items (100) on P list, all items left behind.

    /**
     * tests that all items are excluded from optimal list when appropriate with only one item on potential list
     * @throws Exception
     */
    @Test
    public void allItemsLeftBehindOneItemOnPList() throws Exception {
        CargoItem teapot = new CargoItem(2,"teapot",2);

        CargoItemList julianaItems = new CargoItemList();
        julianaItems.addCargoItemToList(teapot);

        Plane largePlane = new Plane("Large Plane", 1);
        Trip summerTrip = new Trip(largePlane);
        summerTrip.loadAirplane(julianaItems);

        CargoItemList expectedCargoItemList = new CargoItemList();
        assertEquals(expectedCargoItemList.toString(), summerTrip.getPlaneForTrip().getItemsToTake().toString());
        assertEquals(julianaItems.toString(), summerTrip.getCargoListGenerator().getItemsLeftBehind().toString());

    }

    @Test
    public void allItemsLeftBehindIdenticalItemsOnPList() throws Exception { //identical items on potential list and all the items on P list should be left behind
        // I: two identical items on list
        CargoItem nailpolish = new CargoItem(2, "Nail Polish ", 2);
        CargoItem nailpolishRed = new CargoItem(2, "Nail Polish ", 2);

        CargoItemList julianaItems = new CargoItemList();
        julianaItems.addCargoItemToList(nailpolish);
        julianaItems.addCargoItemToList(nailpolishRed);//have identical items on list

        Plane largePlane = new Plane("Large Plane", 1);
        Trip winterTrip = new Trip(largePlane);
        winterTrip.loadAirplane(julianaItems);

        CargoItemList expectedCargoItemList = new CargoItemList();
        expectedCargoItemList.addCargoItemToList(nailpolish);
        expectedCargoItemList.addCargoItemToList(nailpolishRed);

        assertEquals("", winterTrip.getPlaneForTrip().getItemsToTake().toString());
        assertEquals(expectedCargoItemList.toString(), winterTrip.getCargoListGenerator().getItemsLeftBehind().toString());
    }

    /**
     * //Tests to see if at least one proper solution is found when:
     * 1) there are multiple possible solutions
     * 2) two identical items on list of potential items.
     * 3) both identical tems are in the solution
     * @throws Exception
     */
    @Test
    public void twoOrMoreSolutionsIdenticalItemsOnPListAreInSolution() throws Exception {
        CargoItem notebook0 = new CargoItem(3,"notebook0",5);
        CargoItem notebook1 = new CargoItem(2,"notebook1",8);
        CargoItem notebook2 = new CargoItem(3,"notebook2",2);
        CargoItem notebook3 = new CargoItem(2,"notebook3",2);

        CargoItemList julianaItems = new CargoItemList();
        julianaItems.addCargoItemToList(notebook3);
        julianaItems.addCargoItemToList(notebook2);
        julianaItems.addCargoItemToList(notebook0);
        julianaItems.addCargoItemToList(notebook1);

        Plane coolPlane = new Plane("This is a cool plane", 8);
        Trip tokyoTrip = new Trip(coolPlane);
        tokyoTrip.loadAirplane(julianaItems);

        CargoItemList expectedCargoItemList0 = new CargoItemList(); //one possible expected solution
        expectedCargoItemList0.addCargoItemToList(notebook0);
        expectedCargoItemList0.addCargoItemToList(notebook1);
        expectedCargoItemList0.addCargoItemToList(notebook3);

        CargoItemList expectedCargoItemList1 = new CargoItemList(); //second possible expected solution
        expectedCargoItemList1.addCargoItemToList(notebook0);
        expectedCargoItemList1.addCargoItemToList(notebook1);
        expectedCargoItemList1.addCargoItemToList(notebook2);

        HashSet<CargoItem> expectedItemsSet0 = new HashSet<>();
        HashSet<CargoItem> expectedItemsSet1 = new HashSet<>();
        HashSet<CargoItem> actualItemSet = new HashSet<>();

        expectedItemsSet0.addAll(expectedCargoItemList0.getItemArrayList());
        expectedItemsSet1.addAll(expectedCargoItemList1.getItemArrayList());
        actualItemSet.addAll(tokyoTrip.getPlaneForTrip().getItemsToTake().getItemArrayList());

        CargoItemList expectedLeftBehindItems0 = new CargoItemList();
        expectedLeftBehindItems0.addCargoItemToList(notebook3);

        CargoItemList expectedLeftBehindItems1 = new CargoItemList();
        expectedLeftBehindItems1.addCargoItemToList(notebook2);

        assertTrue(actualItemSet.equals(expectedItemsSet0) || actualItemSet.equals(expectedItemsSet1));
        assertTrue(expectedLeftBehindItems0.toString().equals(tokyoTrip.getCargoListGenerator().getItemsLeftBehind().toString()) || expectedLeftBehindItems1.toString().equals(tokyoTrip.getCargoListGenerator().getItemsLeftBehind().toString()));

    }

    /**
     * //Tests to see if at least one proper solution is found when:
     * 1) there are multiple possible solutions
     * 2) two identical items on list of potential items.
     * 3) both identical tems are in the solution
     * @throws Exception
     */
    @Test
    public void twoOrMoreSolutionsIdenticalItemsOnPListNeitherInSolution() throws Exception {
        // L: two identical items on list of potential items. neither item is in the solution
        CargoItem shirt0 = new CargoItem(3,"shirt0",1);
        CargoItem shirt1 = new CargoItem(3,"shirt1",1);
        CargoItem shirt2 = new CargoItem(1,"shirt2",1);
        CargoItem shirt3 = new CargoItem(2,"shirt3",1);
        CargoItemList julianaItems = new CargoItemList();
        julianaItems.addCargoItemToList(shirt0);
        julianaItems.addCargoItemToList(shirt1);
        julianaItems.addCargoItemToList(shirt2);
        julianaItems.addCargoItemToList(shirt3);
        Plane coolPlane = new Plane("This is a cool plane", 8);
        coolPlane.getItemsToTake().clearList();
        coolPlane.setMaxOzWeight(2);

        Trip parisTrip = new Trip(coolPlane);
        parisTrip.loadAirplane(julianaItems);

        HashSet<CargoItem> expectedItemsSet3 = new HashSet<>();
        HashSet<CargoItem> expectedItemsSet4 = new HashSet<>();
        HashSet<CargoItem> actualItemSet1 = new HashSet<>();
        expectedItemsSet3.add(shirt2);
        expectedItemsSet4.add(shirt3);
        actualItemSet1.addAll(parisTrip.getPlaneForTrip().getItemsToTake().getItemArrayList());

        assertTrue(actualItemSet1.equals(expectedItemsSet3) || actualItemSet1.equals(expectedItemsSet4));

        HashSet<CargoItem> expectedItemsLeftSet0 = new HashSet<>(); // solution one
        HashSet<CargoItem> expectedItemsLeftSet1 = new HashSet<>(); // solution two
        HashSet<CargoItem> actualItemsLeftSet = new HashSet<>();

        expectedItemsLeftSet0.add(shirt0);
        expectedItemsLeftSet0.add(shirt1);
        expectedItemsLeftSet0.add(shirt3);

        expectedItemsLeftSet1.add(shirt0);
        expectedItemsLeftSet1.add(shirt1);
        expectedItemsLeftSet1.add(shirt2);

        actualItemsLeftSet.addAll(parisTrip.getCargoListGenerator().getItemsLeftBehind().getItemArrayList());
        assertTrue(actualItemsLeftSet.equals(expectedItemsLeftSet0) || actualItemsLeftSet.equals(expectedItemsLeftSet1));
    }

    @Test
    public void twoSolutionsManyItemsOnPList() throws Exception {
        // ensures code works when
        //there are two solutions
        // there is only one item taken
        // Many items on list
        CargoItem boots0 = new CargoItem(1,"boots0",20);
        CargoItem boots1 = new CargoItem(2,"boots1",20);
        CargoItem boots2 = new CargoItem(3,"boots2",1);
        CargoItem boots3 = new CargoItem(46,"boots3",34);
        CargoItem boots4 = new CargoItem(5,"boots4",56);
        CargoItem boots5 = new CargoItem(6,"boots5",89);
        CargoItem boots6 = new CargoItem(7,"boots6",57);
        CargoItem boots7 = new CargoItem(60,"boots7",900);
        CargoItem boots8 = new CargoItem(34,"boots8",875);
        CargoItem boots9 = new CargoItem(90,"boots9",3);
        CargoItem boots10 = new CargoItem(26,"boots10",6);

        CargoItemList breeList = new CargoItemList();
        breeList.addCargoItemToList(boots0);
        breeList.addCargoItemToList(boots1);
        breeList.addCargoItemToList(boots2);
        breeList.addCargoItemToList(boots3);
        breeList.addCargoItemToList(boots4);
        breeList.addCargoItemToList(boots5);
        breeList.addCargoItemToList(boots6);
        breeList.addCargoItemToList(boots7);
        breeList.addCargoItemToList(boots8);
        breeList.addCargoItemToList(boots9);
        breeList.addCargoItemToList(boots10);

        Plane greenPlane = new Plane("Green Plane", 2);
        Trip yamagataTrip = new Trip(greenPlane);
        yamagataTrip.loadAirplane(breeList);

        HashSet<CargoItem> expectedItemsSet0 = new HashSet<>();// possible solution one
        HashSet<CargoItem> expectedItemsSet1 = new HashSet<>();// possible solution two
        HashSet<CargoItem> actualItemSet0 = new HashSet<>();

        expectedItemsSet0.add(boots0);
        expectedItemsSet1.add(boots1);
        actualItemSet0.addAll(yamagataTrip.getPlaneForTrip().getItemsToTake().getItemArrayList());

        assertTrue(actualItemSet0.equals(expectedItemsSet0) || actualItemSet0.equals(expectedItemsSet1));

        HashSet<CargoItem> expectedItemsLeftSet0 = new HashSet<>(); // solution one
        HashSet<CargoItem> expectedItemsLeftSet1 = new HashSet<>(); // solution two
        HashSet<CargoItem> actualItemsLeftSet = new HashSet<>();

        expectedItemsLeftSet0.add(boots1);
        expectedItemsLeftSet0.add(boots2);
        expectedItemsLeftSet0.add(boots3);
        expectedItemsLeftSet0.add(boots4);
        expectedItemsLeftSet0.add(boots5);
        expectedItemsLeftSet0.add(boots6);
        expectedItemsLeftSet0.add(boots7);
        expectedItemsLeftSet0.add(boots8);
        expectedItemsLeftSet0.add(boots9);
        expectedItemsLeftSet0.add(boots10);

        expectedItemsLeftSet1.add(boots1);
        expectedItemsLeftSet1.add(boots2);
        expectedItemsLeftSet1.add(boots3);
        expectedItemsLeftSet1.add(boots4);
        expectedItemsLeftSet1.add(boots5);
        expectedItemsLeftSet1.add(boots6);
        expectedItemsLeftSet1.add(boots7);
        expectedItemsLeftSet1.add(boots8);
        expectedItemsLeftSet1.add(boots9);
        expectedItemsLeftSet1.add(boots10);

        actualItemsLeftSet.addAll(yamagataTrip.getCargoListGenerator().getItemsLeftBehind().getItemArrayList());
        assertTrue(actualItemsLeftSet.equals(expectedItemsLeftSet0) || actualItemsLeftSet.equals(expectedItemsLeftSet1));
    }

    @Test
    public void oneItemTakenOnePossSolution() throws Exception {
        //Tests to see if the proper solution is found when
        // there should be only one item taken along from list of potential items.
        // One Possible solution.
        // V)  two identical items on list of potential items. neither item is in the solution
        // There are a larger number of items on the list
        // proper item to bring is in the middle of the potential list
        CargoItem boots1 = new CargoItem(20,"boots1",20);
        CargoItem boots2 = new CargoItem(34,"boots2",1);
        CargoItem boots3 = new CargoItem(46,"boots3",34);
        CargoItem boots4 = new CargoItem(500,"boots4",56);
        CargoItem boots5 = new CargoItem(77,"boots5",57);
        CargoItem boots6 = new CargoItem(77,"boots6",57);
        CargoItem boots0 = new CargoItem(4,"boots0",20);
        CargoItem boots7 = new CargoItem(60,"boots7",900);
        CargoItem boots8 = new CargoItem(34,"boots8",875);
        CargoItem boots9 = new CargoItem(90,"boots9",3);
        CargoItem boots10 = new CargoItem(26,"boots10",6);

        CargoItemList DaniList = new CargoItemList();
        DaniList.addCargoItemToList(boots0);
        DaniList.addCargoItemToList(boots1);
        DaniList.addCargoItemToList(boots2);
        DaniList.addCargoItemToList(boots3);
        DaniList.addCargoItemToList(boots4);
        DaniList.addCargoItemToList(boots5);
        DaniList.addCargoItemToList(boots6);
        DaniList.addCargoItemToList(boots7);
        DaniList.addCargoItemToList(boots8);
        DaniList.addCargoItemToList(boots9);
        DaniList.addCargoItemToList(boots10);

        Plane greenPlane = new Plane("Green Plane", 5);
        Trip yamagataTrip = new Trip(greenPlane);
        yamagataTrip.loadAirplane(DaniList);

        HashSet<CargoItem> expectedItemsSet0 = new HashSet<>();
        HashSet<CargoItem> actualItemSet0 = new HashSet<>();

        expectedItemsSet0.add(boots0);
        actualItemSet0.addAll(yamagataTrip.getPlaneForTrip().getItemsToTake().getItemArrayList());

        assertEquals(expectedItemsSet0, actualItemSet0);

        HashSet<CargoItem> expectedItemsLeftSet0 = new HashSet<>();
        HashSet<CargoItem> actualItemsLeftSet = new HashSet<>();

        expectedItemsLeftSet0.add(boots1);
        expectedItemsLeftSet0.add(boots2);
        expectedItemsLeftSet0.add(boots3);
        expectedItemsLeftSet0.add(boots4);
        expectedItemsLeftSet0.add(boots5);
        expectedItemsLeftSet0.add(boots6);
        expectedItemsLeftSet0.add(boots7);
        expectedItemsLeftSet0.add(boots8);
        expectedItemsLeftSet0.add(boots9);
        expectedItemsLeftSet0.add(boots10);

        actualItemsLeftSet.addAll(yamagataTrip.getCargoListGenerator().getItemsLeftBehind().getItemArrayList());

        assertEquals(expectedItemsLeftSet0, actualItemsLeftSet);

    }

    @Test
    public void moreThanOneItemTakenOnePossSolution() throws Exception {
        //Tests to see if the proper solution is found when
        // there should be more than one item taken along from list of potential.
        // One possible solution
        // Z: two identical items on list of potential items. neither item is in the solution
        CargoItem boots1 = new CargoItem(20,"boots1",20); //
        CargoItem boots2 = new CargoItem(340,"boots2",1);
        CargoItem boots3 = new CargoItem(46,"boots3",34); //
        CargoItem boots4 = new CargoItem(500,"boots4",56);
        CargoItem boots5 = new CargoItem(77,"boots5",57);
        CargoItem boots6 = new CargoItem(77,"boots6",57);
        CargoItem boots0 = new CargoItem(4,"boots0",20); //
        CargoItem boots7 = new CargoItem(60,"boots7",9);
        CargoItem boots8 = new CargoItem(34,"boots8",875); //
        CargoItem boots9 = new CargoItem(90,"boots9",3);
        CargoItem boots10 = new CargoItem(26,"boots10",2);

        CargoItemList SallyList = new CargoItemList();
        SallyList.addCargoItemToList(boots0);
        SallyList.addCargoItemToList(boots1);
        SallyList.addCargoItemToList(boots2);
        SallyList.addCargoItemToList(boots3);
        SallyList.addCargoItemToList(boots4);
        SallyList.addCargoItemToList(boots5);
        SallyList.addCargoItemToList(boots6);
        SallyList.addCargoItemToList(boots7);
        SallyList.addCargoItemToList(boots8);
        SallyList.addCargoItemToList(boots9);
        SallyList.addCargoItemToList(boots10);

        Plane japanAirPlane = new Plane("Japan Plane", 105);
        Trip ObanazawaTrip = new Trip(japanAirPlane);
        ObanazawaTrip.loadAirplane(SallyList);

        HashSet<CargoItem> expectedItemsSet0 = new HashSet<>();// possible solution one
        HashSet<CargoItem> actualItemSet0 = new HashSet<>();

        expectedItemsSet0.add(boots8);
        expectedItemsSet0.add(boots3);
        expectedItemsSet0.add(boots1);
        expectedItemsSet0.add(boots0);

        actualItemSet0.addAll(japanAirPlane.getItemsToTake().getItemArrayList());

        assertEquals(expectedItemsSet0, actualItemSet0);

        HashSet<CargoItem> expectedItemsLeftSet0 = new HashSet<>(); // solution one
        HashSet<CargoItem> actualItemsLeftSet = new HashSet<>();

        expectedItemsLeftSet0.add(boots6);
        expectedItemsLeftSet0.add(boots7);
        expectedItemsLeftSet0.add(boots2);
        expectedItemsLeftSet0.add(boots5);
        expectedItemsLeftSet0.add(boots9);
        expectedItemsLeftSet0.add(boots4);
        expectedItemsLeftSet0.add(boots10);

        actualItemsLeftSet.addAll(ObanazawaTrip.getCargoListGenerator().getItemsLeftBehind().getItemArrayList());
        assertEquals(expectedItemsLeftSet0, actualItemsLeftSet);
    }

    // TOdo Test for more than one possible solution,  many items are on list of potential items (100) ?

}
