package com.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.example.VendingMachine.Money;
import com.example.VendingMachineJGivenTest.GivenVendingMachine;
import com.example.VendingMachineJGivenTest.ThenVendingMachine;
import com.example.VendingMachineJGivenTest.WhenVendingMachine;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ExtendedDescription;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

public class VendingMachineJGivenTest extends ScenarioTest<GivenVendingMachine<?>, WhenVendingMachine<?>, ThenVendingMachine> {

    @Test
    public void the_vending_machine_can_be_recharged() throws Exception {
        given().an_empty_vending_machine();
        when().the_machine_is_recharged_with_$_$_notes( 5, Money.FIVE_HUNDRED_EURO )
            .and().the_machine_is_recharged_with_$_$_notes( 2, Money.FIVE_HUNDRED_EURO );
        then().the_machine_contains_$_$_notes( 7, Money.FIVE_HUNDRED_EURO );
    }

    static class GivenVendingMachine<SELF extends GivenVendingMachine<?>> extends Stage<SELF> {
        @ProvidedScenarioState
        private VendingMachine machine;

        public SELF an_empty_vending_machine() {
            machine = new VendingMachine();
            return self();
        }

        @ExtendedDescription( "A vending machine, where each type of money exists exactly ten times" )
        public SELF a_well_filled_vending_machine() {
            machine = new VendingMachine();
            for( Money type : Money.values() ) {
                machine.recharge( type, 10 );
            }
            return self();
        }
    }

    static class WhenVendingMachine<SELF extends WhenVendingMachine<?>> extends Stage<SELF> {
        @ExpectedScenarioState
        private VendingMachine machine;

        public SELF the_machine_is_recharged_with_$_$_notes( int amount, Money noteType ) {
            machine.recharge( noteType, amount );
            return self();
        }

    }

    static class ThenVendingMachine extends Stage<ThenVendingMachine> {
        @ExpectedScenarioState
        private VendingMachine machine;

        public void the_machine_contains_$_$_notes( int amount, Money noteType ) {
            assertEquals( amount, machine.getAvailableAmount( noteType ) );
        }
    }
}
