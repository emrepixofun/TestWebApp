package com.emre.gelirgider.service;

import com.emre.gelirgider.context.UserContext;
import com.emre.gelirgider.dto.SummaryDTO;
import com.emre.gelirgider.dto.TransactionDTO;
import com.emre.gelirgider.mapper.TransactionMapper;
import com.emre.gelirgider.model.Category;
import com.emre.gelirgider.model.Transaction;
import com.emre.gelirgider.model.TransactionType;
import com.emre.gelirgider.model.User;
import com.emre.gelirgider.repository.CategoryRepository;
import com.emre.gelirgider.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    private User currentUser() {
        return UserContext.getUser();
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findByUser(currentUser()).stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByUserAndType(currentUser(), type).stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByUserAndTransactionDateBetween(currentUser(), startDate, endDate).stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findByUserAndId(currentUser(), id)
                .orElseThrow(() -> new RuntimeException("İşlem bulunamadı: " + id));
        return transactionMapper.toDTO(transaction);
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        User user = currentUser();
        Category category = null;
        if (transactionDTO.getCategoryId() != null) {
            category = categoryRepository.findByUserAndId(user, transactionDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + transactionDTO.getCategoryId()));
        }
        
        Transaction transaction = transactionMapper.toEntity(transactionDTO, user, category);
        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toDTO(saved);
    }

    @Transactional
    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findByUserAndId(currentUser(), id)
                .orElseThrow(() -> new RuntimeException("İşlem bulunamadı: " + id));
        
        transaction.setTitle(transactionDTO.getTitle());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(transactionDTO.getType());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        
        if (transactionDTO.getCategoryId() != null) {
            Category category = categoryRepository.findByUserAndId(currentUser(), transactionDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + transactionDTO.getCategoryId()));
            transaction.setCategory(category);
        } else {
            transaction.setCategory(null);
        }
        
        Transaction updated = transactionRepository.save(transaction);
        return transactionMapper.toDTO(updated);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsByUserAndId(currentUser(), id)) {
            throw new RuntimeException("İşlem bulunamadı: " + id);
        }
        transactionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public SummaryDTO getSummary() {
        User user = currentUser();
        BigDecimal totalIncome = transactionRepository.sumByUserAndType(user, TransactionType.GELIR);
        BigDecimal totalExpense = transactionRepository.sumByUserAndType(user, TransactionType.GIDER);
        
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;
        
        BigDecimal balance = totalIncome.subtract(totalExpense);
        
        return new SummaryDTO(totalIncome, totalExpense, balance);
    }

    @Transactional(readOnly = true)
    public SummaryDTO getSummaryByDateRange(LocalDate startDate, LocalDate endDate) {
        User user = currentUser();
        BigDecimal totalIncome = transactionRepository.sumByUserAndTypeAndDateBetween(user, TransactionType.GELIR, startDate, endDate);
        BigDecimal totalExpense = transactionRepository.sumByUserAndTypeAndDateBetween(user, TransactionType.GIDER, startDate, endDate);
        
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;
        
        BigDecimal balance = totalIncome.subtract(totalExpense);
        
        return new SummaryDTO(totalIncome, totalExpense, balance);
    }
}
