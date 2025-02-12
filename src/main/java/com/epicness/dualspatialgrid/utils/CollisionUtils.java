package com.epicness.dualspatialgrid.utils;

import com.badlogic.gdx.math.*;

public class CollisionUtils {

    public static boolean overlaps(float x, float y, float width, float height, Rectangle r) {
        return x <= r.x + r.width && x + width >= r.x && y <= r.y + r.height && y + height >= r.y;
    }

    public static boolean overlaps(Rectangle a, Rectangle b) {
        return overlaps(a.x, a.y, a.width, a.height, b);
    }


    public static boolean overlapPolygonCircle(Polygon polygon, Circle circle) {
        return overlapPolygonCircle(polygon, circle.x, circle.y, circle.radius);
    }

    /* Also returns true if the polygon contains the circle */
    private static boolean overlapPolygonCircle(Polygon polygon, float circleCenterX, float circleCenterY, float circleRadius) {
        float[] vertices = polygon.getTransformedVertices();
        Vector2 start = new Vector2();
        Vector2 end = new Vector2();
        Vector2 center = new Vector2(circleCenterX, circleCenterY);
        float squareRadius = circleRadius * circleRadius;
        /* Loop through the segments of the polygon */
        for (int i = 0; i < vertices.length; i += 2) {
            if (i == 0) {
                start.set(vertices[vertices.length - 2], vertices[vertices.length - 1]);
            } else {
                start.set(vertices[i - 2], vertices[i - 1]);
            }
            end.set(vertices[i], vertices[i + 1]);
            if (Intersector.intersectSegmentCircle(start, end, center, squareRadius)) {
                return true;
            }
        }
        return polygon.contains(center);
    }

    /* Also returns true if the polygon contains the circle */
    public static boolean overlapPolygonCircle(Polygon polygon, Circle circle,
                                                Intersector.MinimumTranslationVector minimumTranslationVector) {
        float[] vertices = polygon.getTransformedVertices();
        Vector2 start = new Vector2();
        Vector2 end = new Vector2();
        /* Loop through the segments of the polygon */
        for (int i = 0; i < vertices.length; i += 2) {
            if (i == 0) {
                start.set(vertices[vertices.length - 2], vertices[vertices.length - 1]);
            } else {
                start.set(vertices[i - 2], vertices[i - 1]);
            }
            end.set(vertices[i], vertices[i + 1]);
            if (Intersector.intersectSegmentCircle(start, end, circle, minimumTranslationVector)) {
                return true;
            }
        }
        return polygon.contains(circle.x, circle.y);
    }
}