package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.dto.WalletDto;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;
import com.leovegas.wallet.exception.WalletNotFoundException;
import com.leovegas.wallet.mapper.WalletMapper;
import com.leovegas.wallet.repository.WalletRepository;
import com.leovegas.wallet.service.WalletService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author volkanozturk
 */
@Service
public class WalletServiceImp implements WalletService {

	private final WalletRepository walletRepository;

	private final WalletMapper walletMapper;

	public WalletServiceImp(WalletRepository walletRepository, WalletMapper walletMapper) {
		this.walletRepository = walletRepository;
		this.walletMapper = walletMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public Wallet getWalletByPlayerId(String playerId) {
		return walletRepository.findWalletByPlayerId(playerId).orElseThrow(() -> new WalletNotFoundException("Wallet not found with Player Id : " + playerId));
	}

	@Override
	public Wallet getWalletLockByPlayerId(String playerId) {
		return walletRepository.findWithLockById(playerId).orElseThrow(() -> new WalletNotFoundException("Wallet not found with Player Id : " + playerId));

	}

	@Override
	public List<WalletDto> getAllPlayersBalance(int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		List<Wallet> wallets = walletRepository.findAll(paging).getContent();
		return walletMapper.toDTOs(wallets);
	}

	@Override
	public List<Transaction> getWalletTransactionByPlayerId(String playerId) {
		return getWalletByPlayerId(playerId).getTransactions();
	}

	@Override
	public Wallet save(Wallet wallet) {
		configureDefaults(wallet);
		return walletRepository.save(wallet);
	}

	private void configureDefaults(Wallet wallet) {
		Date now = new Date();
		wallet.setCreatedAt(now);
		wallet.setLastUpdatedAt(now);
	}
}
