package br.com.felipe.valet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.felipe.valet.model.ValetModel;

public class Main {

    private static List<ValetModel> bancoDeDadosMemoria = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("Quantidade");
        int vagas = 100;
        int opcaoDoMenu = 1;
        
        Scanner entrada = new Scanner(System.in);  // Scanner para entrada do usuário
        
        while (vagas > 0) {
            System.out.println("Escolha a opção do menu");
            System.out.println("1. Inserir placa");
            System.out.println("2. Pagar");
            System.out.println("3. Mostrar total recebido");
            System.out.println("4. Sair");
            System.out.print("Opção: ");
            
            opcaoDoMenu = entrada.nextInt();	
            
            if (opcaoDoMenu == 1) {
                System.out.print("Informe a placa: ");
                String placa = entrada.next(); // Capturando placa
                entradaDeVeiculo(placa); // Registrando veículo
                vagas--; // Decrementa o número de vagas
            } else if (opcaoDoMenu == 2) {
                System.out.print("Informe a placa para pagamento: ");
                String placa = entrada.next(); // Capturando placa
                pagarValet(placa); // Processando pagamento
            } else if (opcaoDoMenu == 3) {
                double totalRecebido = ValetModel.calcularTotal(bancoDeDadosMemoria);
                System.out.println("Total recebido pelo valet: R$ " + totalRecebido);
            } else if (opcaoDoMenu == 4) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida, tente novamente.");
            }

            System.out.println("Quantidade de vagas restantes: " + vagas);
        }

        if (vagas <= 0) {
            System.out.println("Não há mais vagas disponíveis.");
        }

        entrada.close();
    }

    // Método para registrar a entrada de um veículo
    public static void entradaDeVeiculo(String placa) {
        ValetModel novoVeiculo = new ValetModel(); // Cria um novo objeto ValetModel
        novoVeiculo.setPlaca(placa); // Usa o setter para definir a placa
        novoVeiculo.setDataEentrada(LocalDateTime.now()); // Define a data de entrada
        novoVeiculo.setValetPago(false); // Define que o valet não foi pago
        bancoDeDadosMemoria.add(novoVeiculo); // Adiciona o novo veículo à lista
        System.out.println("Veículo com placa " + placa + " adicionado ao sistema.");
    }

    /**
     * Pagar ticket
     * @param placaVeiculo
     */
    public static void pagarValet(String placaVeiculo) {
        try {
            boolean encontrado = false; // Flag para verificar se o veículo foi encontrado
            for (ValetModel valetModel : bancoDeDadosMemoria) {
                if (valetModel.getPlaca().equalsIgnoreCase(placaVeiculo) && !valetModel.getValetPago()) {
                    double valorAPagar = valetModel.calcularPagamento();
                    valetModel.setValetPago(true); // Marca como pago
                    System.out.println("Pagamento realizado. Total: R$ " + valorAPagar);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Veículo não encontrado ou já pago.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}