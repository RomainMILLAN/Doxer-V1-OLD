package fr.skytorstd.doxerbot.embedCrafter;

import com.github.prominence.openweathermap.api.model.weather.Weather;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class WeatherCrafter extends EmbedCrafter{

    public static MessageEmbed craftEmbedWeather(Weather weather){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("⛅️ **Météo: " + weather.getLocation().getName() + "**");
        embed.setThumbnail(weather.getWeatherState().getWeatherIconUrl());
        embed.addField("**Température**", "🌡 " + weather.getTemperature().getValue() + "°C\n▲ " + weather.getTemperature().getMaxTemperature() + "°C\n▼ " + weather.getTemperature().getMinTemperature() + "°C", true);
        embed.addField("**Informations**", "🌬 " + weather.getWind().getSpeed() + "km/h\n🫧 " + weather.getHumidity().getValue() + "%\n🎚 " + weather.getAtmosphericPressure().getValue() + "hPa", true);
        embed.addField("**Ephéméride**", "🌖 " + weather.getLocation().getSunriseTime().getHour() + ":" + weather.getLocation().getSunriseTime().getMinute() + "\n🌒 " + weather.getLocation().getSunsetTime().getHour() + ":" + weather.getLocation().getSunsetTime().getMinute(), true);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

}
