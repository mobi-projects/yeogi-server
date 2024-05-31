package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.member.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
