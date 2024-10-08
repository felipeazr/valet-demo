package br.com.yaman.valet.model;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ValetModel {
    private String placa;
    private LocalDateTime dataEentrada;
    private LocalDateTime dataSaida;
    private Boolean valetPago;

    public ValetModel() {
        // Construtor padrão
    }

    public ValetModel(String placa, LocalDateTime dataEentrada, LocalDateTime dataSaida, Boolean valetPago) {
        this.placa = placa;
        this.dataEentrada = dataEentrada;
        this.dataSaida = dataSaida;
        this.valetPago = valetPago;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalDateTime getDataEentrada() {
        return dataEentrada;
    }

    public void setDataEentrada(LocalDateTime dataEentrada) {
        this.dataEentrada = dataEentrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Boolean getValetPago() {
        return valetPago;
    }

    public void setValetPago(Boolean valetPago) {
        this.valetPago = valetPago;
    }

    public double calcularPagamento() {
        double valorBase = 0.0;
        double valorFinal = 0.0;

        if (dataSaida == null) {
            dataSaida = LocalDateTime.now(); // Se a saída não foi definida, assume agora
        }

        Duration duration = Duration.between(dataEentrada, dataSaida);
        long horas = duration.toHours();
        long minutos = duration.toMinutes() % 60;

        // Cálculo baseado nas horas
        if (horas == 0 && minutos <= 60) {
            valorBase = 22.00; // Cobramos a primeira hora
        } else {
            valorBase = 79 * (horas / 24); // Cálculo para diárias
            if (horas % 24 > 0) {
                valorBase += 12; // Adiciona custo adicional se houver horas a mais
            }
        }

        // Percentuais dos impostos
        double confisPercent = 0.0770;
        double pisPercent = 0.0165;
        double inssPercent = 0.05;

        // Calcula o valor base antes dos impostos
        double valorBaseSemImpostos = valorBase / (1 + confisPercent + pisPercent + inssPercent);

        // Calcula os valores dos impostos com base no valor base
        double confis = valorBaseSemImpostos * confisPercent;
        double pis = valorBaseSemImpostos * pisPercent;
        double inss = valorBaseSemImpostos * inssPercent;

        double totalImpostos = confis + pis + inss;

        // O valor final que o cliente paga já inclui os impostos
        valorFinal = valorBase; // Valor que o cliente vê e paga

        // Exibe os detalhes
        System.out.println("Valor base sem impostos: R$ " + valorBaseSemImpostos);
        System.out.println("Impostos: CONFIS: R$ " + confis + ", PIS: R$ " + pis + ", INSS: R$ " + inss);
        System.out.println("Total de impostos: R$ " + totalImpostos);
        System.out.println("Valor final pago pelo cliente: R$ " + valorFinal);

        return valorFinal; // Retorna o valor final que o cliente paga
    }

    public static double calcularTotal(List<ValetModel> modelos) {
        double total = 0.0;
        for (ValetModel model : modelos) {
            if (model.getValetPago()) {
                total += model.calcularPagamento(); // Adiciona o pagamento calculado ao total
            }
        }
        return total; // Retorna o total acumulado
    }
}