package MoneyUnitsConverter;

import enums.common.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUnitsConverter {

    public static BigDecimal toMajor(Long minorUnits, Currency currency) {
        return BigDecimal.valueOf(minorUnits, currency.getMinorUnit());
    }

    public static Long toMinor(BigDecimal majorUnits, Currency currency) {
        return majorUnits.setScale(currency.getMinorUnit(), RoundingMode.HALF_EVEN)
                .movePointRight(currency.getMinorUnit())
                .longValueExact();
    }
}
