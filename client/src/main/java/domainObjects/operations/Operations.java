package domainObjects.operations;

import domainObjects.accounts.impl.Client;

interface Operations {
   void depositFunds(Client client);
   void withdrawFunds(Client client);
}