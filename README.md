# Trash Store (Spring Boot)

Simple Spring Boot demo where users can browse and "purchase" items made from trash.

Quick start

Build with Maven:

```bash
mvn -B package
```

Run locally:

```bash
java -jar target/trash-store-0.0.1-SNAPSHOT.jar
```

Or build and run with Docker:

```bash
docker build -t trash-store .
docker run -p 8080:8080 trash-store
```

Endpoints

- `GET /api/items` — list items
- `GET /api/items/{id}` — get item
- `POST /api/items` — create item
- `PUT /api/items/{id}` — update item
- `DELETE /api/items/{id}` — delete item
- `POST /api/items/purchase/{id}` — mark item as purchased

Notes

- Uses H2 in-memory DB by default. For production choose a persistent DB and update `application.properties`.

Git & Deployment

- Initialize a git repo locally and push to GitHub:

```bash
git init
git add .
git commit -m "Initial commit: Trash Store"
# create a GitHub repo and add it as remote, then:
git push -u origin main
```

- Recommended (easy & free): Use Render. Steps:
	1. Create a new Web Service on Render and connect your GitHub repo.
	2. Use the default build command `mvn -B -DskipTests package` and start command `java -jar target/trash-store-0.0.1-SNAPSHOT.jar`.
	3. Render will build and provide a public URL for your app.

- If you prefer CI-driven container publishing, this repo includes a GitHub Actions workflow that builds the jar and publishes a Docker image to GitHub Container Registry (GHCR) on push to `main` — see `.github/workflows/ci.yml`.

- Optional: If you connect your Render service, you can add `RENDER_API_KEY` and `RENDER_SERVICE_ID` as GitHub secrets and enable the `render-deploy.yml` workflow to trigger a deploy after successful push.

