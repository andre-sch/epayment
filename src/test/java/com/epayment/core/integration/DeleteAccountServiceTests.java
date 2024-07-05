package com.epayment.core.integration;

import com.epayment.core.domain.*;
import com.epayment.core.application.repositories.AccountRepository;
import com.epayment.core.application.services.DeleteAccountService;
import com.epayment.core.utils.DummyAccountFactory;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteAccountServiceTests {
  private final DummyAccountFactory accountFactory = new DummyAccountFactory();
  @MockBean private EventDispatcher<AccountEvent> eventDispatcher;
  @Autowired private AccountRepository accountRepository;
  @Autowired private DeleteAccountService deleteAccountService;

  @Test
  public void accountCreationSucceeds() {
    // arrange
    var account = accountFactory.build();
    accountRepository.save(account);

    // act
    this.deleteAccountService.execute(account.getEmail());

    // assert
    var accountQuery = accountRepository.findById(account.getId());
    var deletedAccount = accountQuery.get();

    assertThat(deletedAccount.getEmail()).isEqualTo(null);
    assertThat(deletedAccount.getPassword()).isEqualTo(null);
    assertThat(deletedAccount.getFullName()).isEqualTo(null);
    
    Mockito.verify(eventDispatcher).dispatch(AccountDeleted.of(account));
  }
}
