package com.julianjupiter.kitty;

import com.julianjupiter.kitty.http.message.HttpMethod;
import com.julianjupiter.kitty.http.server.ContextHandler;
import com.julianjupiter.kitty.http.server.RequestHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Julian Jupiter
 */
final class KittyRouteCollector implements RouteCollector {
    private final Map<String, List<Route>> routeMap = new HashMap<>();

    @Override
    public Map<String, List<Route>> routes() {
        return Map.copyOf(this.routeMap);
    }

    @Override
    public RouteCollector any(String path, RequestHandler handler) {
        Arrays.stream(HttpMethod.values())
                .forEach(method -> this.createRoute(method, path, handler));
        return this;
    }

    @Override
    public RouteCollector any(String path, ContextHandler handler) {
        var routes = Arrays.stream(HttpMethod.values())
                .map(method -> this.createRoute(method, path, handler))
                .toList();
        this.addRoutes(routes);
        return this;
    }

    @Override
    public RouteCollector anyOf(Set<HttpMethod> methods, String path, RequestHandler handler) {
        var routes = methods.stream()
                .map(method -> this.createRoute(method, path, handler))
                .toList();
        this.addRoutes(routes);
        return this;
    }

    @Override
    public RouteCollector anyOf(Set<HttpMethod> methods, String path, ContextHandler handler) {
        var routes = methods.stream()
                .map(method -> this.createRoute(method, path, handler))
                .toList();
        this.addRoutes(routes);
        return this;
    }

    @Override
    public RouteCollector delete(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.DELETE, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector delete(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.DELETE, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector get(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.GET, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector get(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.GET, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector head(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.HEAD, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector head(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.HEAD, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector options(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.OPTIONS, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector options(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.OPTIONS, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector patch(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.PATCH, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector patch(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.PATCH, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector post(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.POST, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector post(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.POST, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector put(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.PUT, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector put(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.PUT, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector trace(String path, RequestHandler handler) {
        var route = this.createRoute(HttpMethod.TRACE, path, handler);
        this.addRoute(route);
        return this;
    }

    @Override
    public RouteCollector trace(String path, ContextHandler handler) {
        var route = this.createRoute(HttpMethod.TRACE, path, handler);
        this.addRoute(route);
        return this;
    }

    private Route createRoute(HttpMethod method, String path, RequestHandler handler) {
        Handler kittyHandler = handler::handle;
        return new KittyRoute(method, new PathPattern(path), kittyHandler);
    }

    private Route createRoute(HttpMethod method, String path, ContextHandler handler) {
        Handler kittyHandler = (request, response) -> handler.handle(new KittyContext(request, response));
        return new KittyRoute(method, new PathPattern(path), kittyHandler);
    }

    private void addRoute(Route route) {
        this.routeMap.compute(route.path().value(), (key, value) -> {
            if (value == null) {
                value = new ArrayList<>();
            }
            value.add(route);
            return value;
        });
    }

    private void addRoutes(List<Route> routes) {
        routes.forEach(route -> this.routeMap.compute(route.path().value(), (key, value) -> {
            if (value == null) {
                value = new ArrayList<>();
            }
            value.add(route);
            return value;
        }));
    }
}