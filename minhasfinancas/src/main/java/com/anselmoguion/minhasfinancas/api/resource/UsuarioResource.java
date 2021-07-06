package com.anselmoguion.minhasfinancas.api.resource;


import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anselmoguion.minhasfinancas.api.dto.UsuarioDTO;
import com.anselmoguion.minhasfinancas.exception.ErroAutenticacao;
import com.anselmoguion.minhasfinancas.exception.RegraNegocioException;
import com.anselmoguion.minhasfinancas.model.entity.Usuario;
import com.anselmoguion.minhasfinancas.service.LancamentoService;
import com.anselmoguion.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	private UsuarioService service;
	private LancamentoService lancamentoservice;
	
	public UsuarioResource(UsuarioService service, LancamentoService lancamentoservice) {
		this.service = service;
		this.lancamentoservice = lancamentoservice;
	}

	@PostMapping
	public ResponseEntity salvar( @RequestBody UsuarioDTO dto) {
		Usuario usuario = Usuario.builder().nome(dto.getNome())
				                           .email(dto.getEmail())
				                           .senha(dto.getSenha()).build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar( @RequestBody UsuarioDTO dto) {
			
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo( @PathVariable("id") Long id) {
		
    	Optional<Usuario> usuario = service.obterPorId(id);
			
		if (!usuario.isPresent()) {
			return new ResponseEntity( HttpStatus.NOT_FOUND);
		}
		
		BigDecimal saldo = lancamentoservice.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}

}
