package com.gazbert.patterns.behavioural.observer.jdkobserver;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.gazbert.patterns.behavioural.observer.jdkeventing.MarketType;
import com.gazbert.patterns.behavioural.observer.jdkobserver.DollarEuroMarket;
import com.gazbert.patterns.behavioural.observer.jdkobserver.DollarStirlingMarket;
import com.gazbert.patterns.behavioural.observer.jdkobserver.bots.BoATradingBot;
import com.gazbert.patterns.behavioural.observer.jdkobserver.bots.GoldmanTradingBot;
import com.gazbert.patterns.behavioural.observer.jdkobserver.bots.HsbcTradingBot;


/**
 * Demonstrates use of Observer pattern using java.util.Observer.
 * <p>
 * This approach can save you fair bit of boiler-plate coding and is my preferred way of implementing Observer
 * pattern in Java. But, you cannot use java.util.Observervable approach if your Observable/Subject extends some 
 * other class, due to Java's single inheritance model.
 * <p>
 * @author gazbert
 *
 */
public class TestJavaUtilsObserverPattern 
{    
    /**
     * Tests USD GBP market observer.
     */
    @Test
    public void testUsdGbpMarketObserver() {
    	
    	// Different banks create their bots, and register with exchange for latest market bid price
        final GoldmanTradingBot goldman = new GoldmanTradingBot();
       	DollarStirlingMarket.getInstance().addObserver(goldman);
        
        final HsbcTradingBot hsbc = new HsbcTradingBot();
       	DollarStirlingMarket.getInstance().addObserver(hsbc);        
       	
        final BoATradingBot boa = new BoATradingBot();
        // BoA bot is taking the day off... ;-)
       	//DollarStirlingMarketTradePrice.getInstance().registerPriceOberver(boa);                       

        // Assert current price from previous day's close
        final BigDecimal yesterdaysPrice = new BigDecimal(0.60);
        assertTrue(yesterdaysPrice.compareTo(goldman.getLatestUsdGbpMarketBidPrice()) == 0);
        assertTrue(yesterdaysPrice.compareTo(hsbc.getLatestUsdGbpMarketBidPrice()) == 0);
        assertTrue(yesterdaysPrice.compareTo(boa.getLatestUsdGbpMarketBidPrice()) == 0);
        
        // A trade occurs on the exchange, so it notifies any observers of latest market bid price...
        final BigDecimal newPrice = new BigDecimal(0.61);
        
        // In real life, this would come from a proper buy order - I'm just using a bid price to keep it simple!
    	DollarStirlingMarket.getInstance().createNewBuyOrder(newPrice, MarketType.USD_GBP);
        
        // Did they get price update? 
        assertTrue(newPrice.compareTo(goldman.getLatestUsdGbpMarketBidPrice()) == 0);
        assertTrue(newPrice.compareTo(hsbc.getLatestUsdGbpMarketBidPrice()) == 0);
        
        // No change from yesterday
        assertTrue(yesterdaysPrice.compareTo(boa.getLatestUsdGbpMarketBidPrice()) == 0);
        
        // Markets close for the day; bots head out for some much needed Krug...
    	DollarStirlingMarket.getInstance().deleteObserver(goldman);    	
    	DollarStirlingMarket.getInstance().deleteObserver(hsbc);    	
    }		
    
    /**
     * Tests USD Euro market observer.
     */
    @Test
    public void testUsdEuroMarketObserver() {
    	
    	// Different banks create their bots, and register with exchange for latest market bid price
        final GoldmanTradingBot goldman = new GoldmanTradingBot();
        DollarEuroMarket.getInstance().addObserver(goldman);
        
        final HsbcTradingBot hsbc = new HsbcTradingBot();
        DollarEuroMarket.getInstance().addObserver(hsbc);        
       	
        final BoATradingBot boa = new BoATradingBot();
        // BoA bot is taking the day off... ;-)
       	//DollarStirlingMarketTradePrice.getInstance().registerPriceOberver(boa);                       

        // Assert current price from previous day's close
        final BigDecimal yesterdaysPrice = new BigDecimal(0.73);
        assertTrue(yesterdaysPrice.compareTo(goldman.getLatestUsdEurMarketBidPrice()) == 0);
        assertTrue(yesterdaysPrice.compareTo(hsbc.getLatestUsdEurMarketBidPrice()) == 0);
        assertTrue(yesterdaysPrice.compareTo(boa.getLatestUsdEurMarketBidPrice()) == 0);
        
        // A trade occurs on the exchange, so it notifies any observers of latest market bid price...
        final BigDecimal newPrice = new BigDecimal(0.74);
        
        // In real life, this would come from a proper buy order - I'm just using a bid price to keep it simple!
        DollarEuroMarket.getInstance().createNewBuyOrder(newPrice, MarketType.USD_EUR);
        
        // Did they get price update? 
        assertTrue(newPrice.compareTo(goldman.getLatestUsdEurMarketBidPrice()) == 0);
        assertTrue(newPrice.compareTo(hsbc.getLatestUsdEurMarketBidPrice()) == 0);
        
        // No change from yesterday
        assertTrue(yesterdaysPrice.compareTo(boa.getLatestUsdEurMarketBidPrice()) == 0);
        
        // Markets close for the day; bots head out for some much needed Krug...
        DollarEuroMarket.getInstance().deleteObserver(goldman);    	
        DollarEuroMarket.getInstance().deleteObserver(hsbc);    	
    }		
}
