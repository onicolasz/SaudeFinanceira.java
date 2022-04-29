package br.com.saudefinanceira.apresentacao;

import java.util.List;
import java.util.Scanner;

import br.com.saudefinanceira.model.Conta_Empresa;
import br.com.saudefinanceira.model.Conta_Pessoal;
import br.com.saudefinanceira.model.Despesas;
import br.com.saudefinanceira.model.Funcionarios;
import br.com.saudefinanceira.model.Rendas;
import br.com.saudefinanceira.model.Usuario;
import br.com.saudefinanceira.persistencia.Conta_EmpresaDAO;
import br.com.saudefinanceira.persistencia.Conta_PessoalDAO;
import br.com.saudefinanceira.persistencia.DespesasDAO;
import br.com.saudefinanceira.persistencia.FuncionariosDAO;
import br.com.saudefinanceira.persistencia.RendasDAO;
import br.com.saudefinanceira.persistencia.UsuarioDAO;

public class Main {

	public static void main(String[] args) {
		
		Scanner teclado = new Scanner(System.in);

		int x;

		int a = 0;

		while (a < 1) {

			System.out.println("");
			System.out.println("1 = Conta de Usuario");
			System.out.println("2 = Conta de Empresa");
			System.out.println("3 = Sair");

			x = teclado.nextInt();

			switch (x) {

			case 1:
				
				System.out.println("");
				System.out.println("1 = Login");
				System.out.println("2 = Cadastrar");
				System.out.println("3 = Voltar");
				
			    int d = teclado.nextInt();

			    if (d == 1) {
					    System.out.println("Digite seu email:");
					    String loginemail = teclado.next();
					 
					    System.out.println("Digite sua senha:");
					    String loginsenha = teclado.next();
					
					    UsuarioDAO uDAO = new UsuarioDAO();
					    Usuario usuario = uDAO.buscarPorEmailSenha(loginemail, loginsenha);	
					
					    if(loginemail.equals(usuario.getEmail()) && loginsenha.equals(usuario.getSenha())) {
					    	
						
						   while (a < 1) {
							   Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
							   Conta_Pessoal conta_pessoal = cpDAO.buscarPorIdUsuario(usuario.getId());
							   if(conta_pessoal == null) {
								   System.out.println("Este usuario nao tem uma conta Pessoal");
								   break;
							   }
				        	   System.out.println("");
						       System.out.println("Bem Vindo " +usuario.getNome());
					    	   System.out.println("");
						       System.out.println("Numero da Conta: " +usuario.getId());
						       System.out.println("Tipo de Conta: " +conta_pessoal.getTipo_conta());
						       System.out.println("Seu Saldo: " +conta_pessoal.getSaldo());
					           System.out.println("Sua Meta é: " +conta_pessoal.getMeta());
					           System.out.println("");
					           if(conta_pessoal.getMeta() <= conta_pessoal.getSaldo()) {
					        	   System.out.println("Você esta dentro da sua Meta!");
					           } else {
					        	   System.out.println("Você esta abaixo da sua Meta, cuidado!");
					           }
						       System.out.println("");
						       System.out.println("1 - Adicionar Renda");
						       System.out.println("2 - Adicionar Despesa");
						       System.out.println("3 - Traçar Meta");
						       System.out.println("4 - Listar Rendas");
						       System.out.println("5 - Listar Despesas");
						       System.out.println("6 - Excluir Renda pelo ID");
					           System.out.println("7 - Excluir Despesa pelo ID");
					           System.out.println("8 - Editar Senha");
						       System.out.println("9 - Excluir Tudo");
						       System.out.println("0 - Fechar");
						
						       int b = teclado.nextInt();
						
						       if (b == 1) {
							       System.out.println("Qual valor de renda você quer adicionar?");
							       double valor = teclado.nextDouble();
							       System.out.println("Qual palavra define sua renda? (Exemplo: Salário, Venda)");
							       String classifica = teclado.nextLine();
							       classifica = teclado.nextLine();
							       RendasDAO rDAO = new RendasDAO();
							       Rendas renda = new Rendas(0, valor, classifica, conta_pessoal, null, null);
							       conta_pessoal = cpDAO.buscarPorId(conta_pessoal.getId());
									renda.setId_conta_pessoal(conta_pessoal);
							        renda = rDAO.salvar(renda);
						       }
						       if (b == 2) {
							       System.out.println("Qual valor de despesa você quer adicionar?");
						 	       double valor = teclado.nextDouble();
						       	   System.out.println("Qual palavra define sua depesa? (Exemplo: Conta de Luz, Conta de Água)");
							       String classifica = teclado.nextLine();
							       classifica = teclado.nextLine();
							       DespesasDAO deDAO = new DespesasDAO();
							       Despesas despesa = new Despesas(0, valor, classifica, conta_pessoal, null, null);
							       conta_pessoal = cpDAO.buscarPorId(conta_pessoal.getId());
							       despesa.setId_conta_pessoal(conta_pessoal);
							       despesa = deDAO.salvar(despesa);
						       }
						        if (b == 3) {
							        System.out.println("Qual valor de Meta você deseja traçar?");
							        double valor = teclado.nextDouble();
							        System.out.println("A meta que você traçou é: "+conta_pessoal.getMeta());
							        conta_pessoal.setMeta(valor);
							        cpDAO.editar(conta_pessoal);
						        }
						        if (b == 4) {
						        	RendasDAO rDAO = new RendasDAO();
									List<Rendas> listaRendas = rDAO.buscarTodosPorIdPessoal(conta_pessoal.getId());

									for (Rendas rendas : listaRendas) {
										System.out.println("");
										System.out.println("ID da Rendas: "+rendas.getId() + " - " + rendas.getValor() + " - "
												+ rendas.getClassifica());
									}
									System.out.println("");
									System.out.println("Digite 1 para voltar:");
									int c = teclado.nextInt();
									if (c == 1) {
										System.out.println("");
									}
						        }
						        if (b == 5) {
							        DespesasDAO deDAO = new DespesasDAO();
							        List<Despesas> listaDespesas = deDAO.buscarTodosPorIdPessoal(conta_pessoal.getId());
							        
							        for (Despesas despesas : listaDespesas) {
										System.out.println("");
										System.out.println("ID da Despesa: "+despesas.getId() + " - " + despesas.getValor() + " - "
												+ despesas.getClassifica());
									}
									System.out.println("");
									System.out.println("Digite 1 para voltar:");
									int c = teclado.nextInt();
									if (c == 1) {
										System.out.println("");
									}
						        }
						        if (b == 6) {
						        	System.out.println("");
						        	System.out.println("(Você pode descobrir o ID da Rendas listando suas Rendas.)");
                                    System.out.println("Digite o ID da Renda:");

									int excluirrenda = teclado.nextInt();
									RendasDAO rDAO = new RendasDAO();
									
									if (rDAO.buscarPorId(excluirrenda).getId_conta_pessoal().getId() == conta_pessoal.getId()) {
										
										boolean excluiu = rDAO.excluir(excluirrenda);
										if (excluiu) {
											System.out.println("Renda excluida com sucesso!");
										} else {
											System.out.println("Nao foi possivel excluir a Renda.");
										}

									} else {
										System.out.println("Nao foi possivel excluir a Renda.");
									}
						        }
						        if (b == 7) {
						        	System.out.println("");
						        	System.out.println("(Você pode descobrir o ID da Despesa listando suas Despesas.)");
                                    System.out.println("Digite o ID da Despesa:");

									int excluirdespesa = teclado.nextInt();

									DespesasDAO deDAO = new DespesasDAO();

									if (deDAO.buscarPorId(excluirdespesa).getId_conta_pessoal().getId() == conta_pessoal.getId()) {

										boolean excluiu = deDAO.excluir(excluirdespesa);
										if (excluiu) {
											System.out.println("Renda excluida com sucesso!");
										} else {
											System.out.println("Nao foi possivel excluir a Renda.");
										}

									} else {
										System.out.println("Nao foi possivel excluir a Renda.");
									}
						        }
								if (b == 8) {
							        System.out.println("");
							        System.out.println("(Você pode descobrir seu numero da conta na tela inicial)");
	                                System.out.println("Digite o numero da conta:");
	                                
	                                int numeroconta = teclado.nextInt();  
	                                
	                                if (uDAO.buscarPorId(numeroconta).getId() == conta_pessoal.getId_usuario().getId()) {
	                					System.out.println("Digite sua senha:");
	                					String senha = teclado.next();		
	                					boolean editou = uDAO.editarSenha(senha, conta_pessoal.getId_usuario());
	                					if (editou) {
	                						System.out.println("Senha alterada com sucesso!");
										} else {
											System.out.println("Nao foi possivel editar senha");
										}
	                                } else {
	                                	System.out.println("Este numero pertence a outra conta");
	                                }
						        }
						        if (b == 9) {
						        	System.out.println("Tem certeza que deseja excluir?");
						        	System.out.println("1 - Sim");
						        	System.out.println("2 - Não");
						        	int certeza = teclado.nextInt();						        
						        	if(certeza == 1) {
						        	uDAO.excluir(usuario.getId());
						        	cpDAO.excluir(conta_pessoal.getId());						        	
						        	System.out.println("Tudo excluido com sucesso!");
						        	break;
						        	} else {
						        		System.out.println("Nao foi possível excluir tudo");
						        	}
						        }
						        if (b == 0) {
									break;
								}
						   }
					  }
			    }
			    if (d == 2) {
			    	
					System.out.println("Digite seu nome completo:");
					String nome = teclado.nextLine();
					nome = teclado.nextLine();
					System.out.println("Digite seu cpf:");
					String cpf = teclado.next();
					System.out.println("Digite seu email:");
					String email = teclado.next();
					System.out.println("Digite sua senha:");
					String senha = teclado.next();				
					System.out.println("Digite sua data de nascimento:");
					String data_nascimento = teclado.next();
					System.out.println("Digite sua profissao:");
					String profissao = teclado.next();
					
					Usuario usuario = new Usuario(0, nome, cpf, email, senha, data_nascimento, profissao);
					UsuarioDAO uDAO = new UsuarioDAO();
					usuario = uDAO.salvar(usuario);
					
					 Conta_Pessoal conta_pessoal = new Conta_Pessoal(0, 0, 0, "Pessoal", usuario, null);
					 Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
					 conta_pessoal = cpDAO.salvar(conta_pessoal);
					    	 
					System.out.println(usuario.getId() > 0 ? "Cadastrado!" : "Erro ao cadastrar!");
				}
				if (d == 3) {
					break;
				}
				break;
				
			case 2:
				
				System.out.println("");
				System.out.println("1 = Login");
				System.out.println("2 = Cadastrar");
				System.out.println("3 = Voltar");
				
			    int v = teclado.nextInt();

			    if (v == 1) {
					    System.out.println("Digite seu email:");
					    String loginemail = teclado.next();
					 
					    System.out.println("Digite sua senha:");
					    String loginsenha = teclado.next();
					    UsuarioDAO uDAO = new UsuarioDAO();
					    Usuario usuario = uDAO.buscarPorEmailSenha(loginemail, loginsenha);	
					    
					    if(loginemail.equals(usuario.getEmail()) && loginsenha.equals(usuario.getSenha())) {
					    						    	
						   while (a < 1) {
							   Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();							    
							    Conta_Empresa conta_empresa = ceDAO.buscarPorIdUsuario(usuario.getId());
							    if(conta_empresa == null) {
							    	System.out.println("Este usuario nao tem uma conta de Empresa");
							    	break;
							    }
				        	   System.out.println("");
						       System.out.println("Bem Vindo " +usuario.getNome());
						       System.out.println("");
						       System.out.println("Nome da Empresa: " +conta_empresa.getNome_empresa());
						       System.out.println("Numero da Conta: " +usuario.getId());
						       System.out.println("Tipo de Conta: " +conta_empresa.getTipo_conta());
						       System.out.println("CNPJ: " +conta_empresa.getCnpj());
						       System.out.println("Seu Saldo: " +conta_empresa.getSaldo());
					           System.out.println("Sua Meta é: " +conta_empresa.getMeta());
					           System.out.println("");
					           if(conta_empresa.getMeta() <= conta_empresa.getSaldo()) {
					        	   System.out.println("Você esta dentro da sua Meta!");
					           } else {
					        	   System.out.println("Você esta abaixo da sua Meta, cuidado!");
					           }
						       System.out.println("");
						       System.out.println("1 - Adicionar Renda");
						       System.out.println("2 - Adicionar Despesa");
						       System.out.println("3 - Adicionar Funcionario");
						       System.out.println("4 - Traçar Meta");
						       System.out.println("5 - Listar Rendas");
						       System.out.println("6 - Listar Despesas");
						       System.out.println("7 - Listar Funcionarios");
						       System.out.println("8 - Excluir Renda pelo ID");
					           System.out.println("9 - Excluir Despesa pelo ID");
					           System.out.println("10 - Excluir Funcionario pelo ID");
					           System.out.println("11 - Editar Senha");
						       System.out.println("12 - Excluir Tudo");
						       System.out.println("0 - Fechar");
						
						       int b = teclado.nextInt();
						
						       if (b == 1) {
							       System.out.println("Qual valor de renda você quer adicionar?");
							       double valor = teclado.nextDouble();
							       System.out.println("Qual palavra define sua renda? (Exemplo: Lucro, Venda)");
							       String classifica = teclado.nextLine();
							       classifica = teclado.nextLine();
							       RendasDAO rDAO = new RendasDAO();
							       Rendas renda = new Rendas(0, valor, classifica, null, conta_empresa, null);
							       conta_empresa = ceDAO.buscarPorId(conta_empresa.getId());
									renda.setId_conta_empresa(conta_empresa);
							        renda = rDAO.salvar(renda);
						       }
						       if (b == 2) {
							       System.out.println("Qual valor de despesa você quer adicionar?");
						 	       double valor = teclado.nextDouble();
						       	   System.out.println("Qual palavra define sua despesa? (Exemplo: Conta de Luz, Mercadoria)");
							       String classifica = teclado.nextLine();
							       classifica = teclado.nextLine();
							       DespesasDAO deDAO = new DespesasDAO();
							       Despesas despesa = new Despesas(0, valor, classifica, null, conta_empresa, null);
							       conta_empresa = ceDAO.buscarPorId(conta_empresa.getId());
							       despesa.setId_conta_empresa(conta_empresa);
							       despesa = deDAO.salvar(despesa);
						       }
						       if (b == 3) {
						    	   System.out.println("Qual o nome do funcionario você quer adicionar?");
						 	       String nome = teclado.nextLine();
						 	       nome = teclado.nextLine();
						       	   System.out.println("Qual a funçao?");
							       String funcao = teclado.next();
							       System.out.println("Qual o Salario?");
						 	       double salario = teclado.nextDouble();
							       FuncionariosDAO fDAO = new FuncionariosDAO();
							       Funcionarios funcionario = new Funcionarios(0, nome, funcao, salario, conta_empresa, null);
							       conta_empresa = ceDAO.buscarPorId(conta_empresa.getId());
							       funcionario.setId_conta_empresa(conta_empresa);
							       funcionario = fDAO.salvar(funcionario);
						        }
						        if (b == 4) {
							        System.out.println("Qual valor de Meta você deseja traçar?");
							        double valor = teclado.nextDouble();
							        System.out.println("A meta que você traçou é: "+conta_empresa.getMeta());
							        conta_empresa.setMeta(valor);
							        ceDAO.editar(conta_empresa);
						        }
						        if (b == 5) {
						        	RendasDAO rDAO = new RendasDAO();
									List<Rendas> listaRendas = rDAO.buscarTodosPorIdEmpresa(conta_empresa.getId());

									for (Rendas rendas : listaRendas) {
										System.out.println("");
										System.out.println("ID da Rendas: "+rendas.getId() + " - " + rendas.getValor() + " - "
												+ rendas.getClassifica());
									}
									System.out.println("");
									System.out.println("Digite 1 para voltar:");
									int c = teclado.nextInt();
									if (c == 1) {
										System.out.println("");
									}
						        }
						        if (b == 6) {
							        DespesasDAO deDAO = new DespesasDAO();
							        List<Despesas> listaDespesas = deDAO.buscarTodosPorIdEmpresa(conta_empresa.getId());
							        
							        for (Despesas despesas : listaDespesas) {
										System.out.println("");
										System.out.println("ID da Despesa: "+despesas.getId() + " - " + despesas.getValor() + " - "
												+ despesas.getClassifica());
									}
									System.out.println("");
									System.out.println("Digite 1 para voltar:");
									int c = teclado.nextInt();
									if (c == 1) {
										System.out.println("");
									}
						        }
						        if (b == 7) {
							        FuncionariosDAO fDAO = new FuncionariosDAO();
							        List<Funcionarios> listaFuncionarios = fDAO.buscarTodosPorId(conta_empresa.getId());
							        
							        for (Funcionarios funcionarios : listaFuncionarios) {
										System.out.println("");
										System.out.println("ID do Funcionario: "+funcionarios.getId() + " - " + funcionarios.getSalario() + " - "
												+ funcionarios.getNome() + " - "  + funcionarios.getFuncao());
									}
									System.out.println("");
									System.out.println("Digite 1 para voltar:");
									int c = teclado.nextInt();
									if (c == 1) {
										System.out.println("");
									}
						        }
						        if (b == 8) {
						        	System.out.println("");
						        	System.out.println("(Você pode descobrir o ID da Rendas listando suas Rendas.)");
                                    System.out.println("Digite o ID da Renda:");

									int excluirrenda = teclado.nextInt();

									RendasDAO rDAO = new RendasDAO();
			
									if (rDAO.buscarPorId(excluirrenda).getId_conta_empresa().getId() == conta_empresa.getId()) {

										boolean excluiu = rDAO.excluir(excluirrenda);
										if (excluiu) {
											System.out.println("Renda excluida com sucesso!");
										} else {
											System.out.println("Nao foi possivel excluir a Renda.");
										}

									} else {
										System.out.println("Nao foi possivel excluir a Renda.");
									}
						        }
						        if (b == 9) {
						        	System.out.println("");
						        	System.out.println("(Você pode descobrir o ID da Despesa listando suas Despesas.)");
                                    System.out.println("Digite o ID da Despesa:");

									int excluirdespesa = teclado.nextInt();

									DespesasDAO deDAO = new DespesasDAO();

									if (deDAO.buscarPorId(excluirdespesa).getId_conta_empresa().getId() == conta_empresa.getId()) {

										boolean excluiu = deDAO.excluir(excluirdespesa);
										if (excluiu) {
											System.out.println("Despesa excluida com sucesso!");
										} else {
											System.out.println("Nao foi possivel excluir a despesa.");
										}

									} else {
										System.out.println("Nao foi possivel excluir a despesa.");
									}
						        }
						        if (b == 10) {
						        	System.out.println("");
						        	System.out.println("(Você pode descobrir o ID do Funcionario listando seus Funcionarios.)");
                                    System.out.println("Digite o ID do Funcionario:");

									int excluirfuncionario = teclado.nextInt();

									FuncionariosDAO fDAO = new FuncionariosDAO();

									if (fDAO.buscarPorId(excluirfuncionario).getId_conta_empresa().getId() == conta_empresa.getId()) {

										boolean excluiu = fDAO.excluir(excluirfuncionario);
										if (excluiu) {
											System.out.println("Funcionario excluida com sucesso!");
										} else {
											System.out.println("Nao foi possivel excluir a funcionario.");
										}

									} else {
										System.out.println("Nao foi possivel excluir a funcionario.");
									}
						        }
						        if (b == 11) {
							        System.out.println("");
							        System.out.println("(Você pode descobrir seu numero da conta na tela inicial)");
	                                System.out.println("Digite o numero da conta:");
	                                
	                                int numeroconta = teclado.nextInt();  
	                                
	                                if (uDAO.buscarPorId(numeroconta).getId() == conta_empresa.getId_usuario().getId()) {
	                					System.out.println("Digite sua senha:");
	                					String senha = teclado.next();		
	                					boolean editou = uDAO.editarSenha(senha, conta_empresa.getId_usuario());
	                					if (editou) {
	                						System.out.println("Senha alterada com sucesso!");
										} else {
											System.out.println("Nao foi possivel editar senha");
										}
	                                } else {
	                                	System.out.println("Numero não corresponde a sua conta");
	                                }
						        }
					        if (b == 12) {
					        	System.out.println("Tem certeza que deseja excluir?");
					        	System.out.println("1 - Sim");
					        	System.out.println("2 - Não");
					        	int certeza = teclado.nextInt();						        
					        	if(certeza == 1) {
					        	uDAO.excluir(usuario.getId());
					        	ceDAO.excluir(conta_empresa.getId());						        	
					        	System.out.println("Tudo excluido com sucesso!");
					        	break;
					        	} else {
					        		System.out.println("Nao foi possível excluir tudo");
					        	}
					        }
					        if (b == 0) {
								break;
							}					        
						  }
					   }
			    }
			    if (v == 2) {
			    	
					System.out.println("Digite seu nome completo:");
					String nome = teclado.nextLine();
					nome = teclado.nextLine();
					System.out.println("Digite seu cpf:");
					String cpf = teclado.next();
					System.out.println("Digite o nome da empresa:");
					String nome_empresa = teclado.nextLine();
					nome_empresa = teclado.nextLine();
					System.out.println("Digite o cnpj da empresa:");
					String cnpj = teclado.next();
					System.out.println("Digite seu email:");
					String email = teclado.next();
					System.out.println("Digite sua senha:");
					String senha = teclado.next();				
					System.out.println("Digite sua data de nascimento:");
					String data_nascimento = teclado.next();
					
					Usuario usuario = new Usuario(0, nome, cpf, email, senha, data_nascimento, null);
					UsuarioDAO uDAO = new UsuarioDAO();
					usuario = uDAO.salvar(usuario);
					
					 Conta_Empresa conta_empresa = new Conta_Empresa(0, 0, 0, nome_empresa, cnpj, "Empresa", usuario, null);
					 Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();		 
					 conta_empresa = ceDAO.salvar(conta_empresa);
					    
					System.out.println(usuario.getId() > 0 ? "Cadastrado!" : "Erro ao cadastrar!");
				}
				if (v == 3) {
					break;
				}
				break;
			case 3:
				System.exit(0);
		 } 
	   }
	}
}