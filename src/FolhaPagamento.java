import java.util.ArrayList;
import java.util.Scanner;

public class FolhaPagamento {
    static Scanner entrada = new Scanner(System.in);
    static final double salarioBase = 2000.00;
    static ArrayList<Colaborador> colaboradores = new ArrayList<>();

    public static void main(String[] args) {
        menu();
    }

    // ============ CLASSES ============
    static class Colaborador {
        String nome;
        int matricula;
        double extras;
        String tipo;

        public Colaborador(String nome, int matricula, double extras, String tipo) {
            this.nome = nome;
            this.matricula = matricula;
            this.extras = extras;
            this.tipo = tipo;
        }

        public double getSalarioFinal() {
            return salarioBase + extras;
        }

    }

    // ============ MENU ============
    public static void menu() {
        int opcao;
        do {
            System.out.println("\n=== FOLHA DE PAGAMENTO ===");
            System.out.println("1 - Cadastrar Funcionário Padrão");
            System.out.println("2 - Cadastrar Funcionário Comissionado");
            System.out.println("3 - Cadastrar Funcionário de Produção");
            System.out.println("4 - Gerar Folha de Pagamento");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPadrao();
                    break;
                case 2:
                    cadastrarComissionado();
                    break;
                case 3:
                    cadastrarProducao();
                    break;
                case 4:
                    gerarFolha();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);
    }

    // ============ CADASTROS ============
    public static void cadastrarPadrao() {
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        int matricula = lerMatricula();

        colaboradores.add(new Colaborador(nome, matricula, 0.0, "Padrão"));
        System.out.println("✔ Funcionário padrão cadastrado!");

    }

    public static void cadastrarComissionado() {
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        int matricula = lerMatricula();

        System.out.print("Informe valor de vendas: ");
        double vendas = entrada.nextDouble();
        while (vendas < 0) {
            System.out.print("Valor inválido. Informe o valor de vendas novamente: ");
            vendas = entrada.nextDouble();
        }


        double percentual;
        do {
            System.out.print("Informe comissão percentual: ");
            percentual = entrada.nextDouble();
            if (percentual < 0) System.out.println("Valor inválido.");
        } while (percentual < 0);
        entrada.nextLine();
        double comissao = calcularComissao(vendas, percentual);


        colaboradores.add(new Colaborador(nome, matricula, comissao, "Comissionado"));
        System.out.println("✔ Funcionário comissionado cadastrado!");

    }

    public static void cadastrarProducao() {
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        int matricula = lerMatricula();
        int quantidadePecas;
        do {
            System.out.print("Informe quantidade de peças: ");
            quantidadePecas = entrada.nextInt();
            if (quantidadePecas < 0) System.out.println("Valor Inválido.");
        } while (quantidadePecas < 0);


        double valorPecas;
        do {
            System.out.print("Informe valor da peça: ");
            valorPecas = entrada.nextDouble();
            if (valorPecas < 0) System.out.println("Valor inválido.");
        } while (valorPecas < 0);
        double bonus = calcularBonus(valorPecas, quantidadePecas);

        colaboradores.add(new Colaborador(nome, matricula, bonus, "Produção"));
        System.out.println("✔ Funcionário produção cadastrado!");

    }

    // ============ FOLHA ============
    public static void gerarFolha() {
        if (colaboradores.isEmpty()) {
            System.out.print("\n Nenhum colaborador cadastrado.");
            return;
        }
        System.out.println("\n===== FOLHA DE PAGAMENTO =====");
        System.out.println("Total de pessoas cadastradas: " + colaboradores.size());

        for (Colaborador colaborador : colaboradores) {
            System.out.println("Nome: " + colaborador.nome);
            System.out.println("Matrícula: " + colaborador.matricula);
            System.out.println("Tipo: " + colaborador.tipo);
            System.out.println("Salário base: R$" + String.format("%.2f", salarioBase));

            if (colaborador.tipo.equals("Comissionado")) {
                System.out.println("Comissâo: R$" + String.format("%.2f", colaborador.extras));
            } else if (colaborador.tipo.equals("Produção")) {
                System.out.println("Produtividade: R$" + String.format("%.2f", colaborador.extras));
            } else {
                System.out.println("Extras: R$0.00");
            }
            System.out.println("Salário Final: R$" + String.format("%.2f", colaborador.getSalarioFinal()));
            System.out.println("------------------------------");

        }
    }

    // ============ CÁLCULOS ============
    public static double calcularComissao(double vendas, double percentual) {
        double comissao = (vendas * percentual) / 100;
        return comissao;
    }

    public static double calcularBonus(double valorPorPeca, int quantidadeProduzida) {
        double bonus = (valorPorPeca * quantidadeProduzida);
        return bonus;
    }

    // ============ UTILITÁRIOS ============
    public static int lerMatricula() {
        int matricula;
        do {
            System.out.print("Matricula: ");
            matricula = entrada.nextInt();

            if (matricula <= 0) System.out.print("Matricula inválida.");
        } while (matricula <= 0);
        entrada.nextLine();
        return matricula;
    }
}