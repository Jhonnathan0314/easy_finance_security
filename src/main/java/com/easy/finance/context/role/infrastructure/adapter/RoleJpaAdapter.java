package com.easy.finance.context.role.infrastructure.adapter;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.context.role.infrastructure.mappers.RoleCreateMapper;
import com.easy.finance.context.role.infrastructure.mappers.RoleMapper;
import com.easy.finance.context.role.infrastructure.mappers.RoleUpdateMapper;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.context.role.infrastructure.persistence.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleJpaAdapter implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final RoleMapper roleMapper = new RoleMapper();
    private final RoleCreateMapper roleCreateMapper = new RoleCreateMapper();
    private final RoleUpdateMapper roleUpdateMapper = new RoleUpdateMapper();

    @Override
    public List<Role> findAll() {
        List<RoleEntity> roleEntities = roleJpaRepository.findAll();
        return roleMapper.entitiesToModels(roleEntities);
    }

    @Override
    public Optional<Role> findById(Long id) {
        Optional<RoleEntity> roleEntity = roleJpaRepository.findById(id);
        return roleEntity.map(roleMapper::entityToModel);
    }

    @Override
    public Optional<Role> findByName(String name) {
        Optional<RoleEntity> roleEntity = roleJpaRepository.findByName(name);
        return roleEntity.map(roleMapper::entityToModel);
    }

    @Override
    public Role create(Role role) {
        RoleEntity roleEntity = roleJpaRepository.save(roleCreateMapper.modelToEntity(role));
        return roleMapper.entityToModel(roleEntity);
    }

    @Override
    public Role update(Role role) {
        RoleEntity roleEntity = roleJpaRepository.save(roleUpdateMapper.modelToEntity(role));
        return roleMapper.entityToModel(roleEntity);
    }

    @Override
    public void deleteById(Long id) {
        roleJpaRepository.deleteById(id);
    }
}
