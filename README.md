# steem-java-api-wrapper
A Java API-Wrapper for the Steem websocket API. 

This project allows you to easily access data stored in the Steem blockchain. The project has been initialized by <a href="https://steemit.com/@dez1337">dez1337 on steemit.com</a>.

# Example
The following code is a small example on how to use this Wrapper.

```Java
package dez.steemit.com;

import dez.steemit.com.configuration.SteemApiWrapperConfig;
import dez.steemit.com.exceptions.SteemConnectionException;
import dez.steemit.com.exceptions.SteemTimeoutException;
import dez.steemit.com.exceptions.SteemTransformationException;
import dez.steemit.com.models.AccountCount;
import dez.steemit.com.models.AccountVotes;

public class SteemAPIUsageExample {
	public static void main(String args[]) {
		// Use the default settings
		SteemApiWrapperConfig myConfig = new SteemApiWrapperConfig();
		try {
			SteemApiWrapper saw = new SteemApiWrapper(myConfig);
			AccountVotes av = saw.getAccountVotes("dez1337");
			AccountCount ac = saw.getAccountCount();
			
			System.out.println("The user dez1337 is one of " + ac.getCount() + " users and has done " + av.getVotes().length + " votes so far.");
			
		} catch (SteemConnectionException | SteemTimeoutException | SteemTransformationException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
		}
	}
}
```
