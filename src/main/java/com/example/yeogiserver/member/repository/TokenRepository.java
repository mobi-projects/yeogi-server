package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.commom.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
