package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
    List<ConditionalExpression> query;

    public QueryFilter(List<ConditionalExpression> query) {
        this.query = query;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression exp : query) {
            if (!exp.getComparisonOperator().satisfied(exp.getFieldGetter().get(record), exp.getStringLiteral())) {
                return false;
            }
        }
        return true;
    }
}
