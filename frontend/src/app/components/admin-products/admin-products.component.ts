import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Product, ProductRequest } from '../../models/product.model';
import { ProductService } from '../../core/services/product.service';

const EMPTY_FORM: ProductRequest = {
  name: '', description: '', price: 0, stockQuantity: 0, category: '', imageUrl: '',
};

@Component({
  selector: 'app-admin-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-products.component.html',
  styleUrl: './admin-products.component.css',
})
export class AdminProductsComponent implements OnInit {
  products: Product[] = [];
  loading = true;
  errorMessage = '';
  saving = false;

  editingId: number | null = null;
  form: ProductRequest = { ...EMPTY_FORM };

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getAll().subscribe({
      next: (products) => {
        this.products = products;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Could not load products.';
        this.loading = false;
      },
    });
  }

  startCreate(): void {
    this.editingId = 0; // 0 = "creating new"
    this.form = { ...EMPTY_FORM };
  }

  startEdit(product: Product): void {
    this.editingId = product.id;
    this.form = {
      name: product.name,
      description: product.description,
      price: product.price,
      stockQuantity: product.stockQuantity,
      category: product.category,
      imageUrl: product.imageUrl,
    };
  }

  cancelEdit(): void {
    this.editingId = null;
    this.form = { ...EMPTY_FORM };
  }

  save(): void {
    this.saving = true;
    this.errorMessage = '';

    const request$ = this.editingId
      ? this.productService.update(this.editingId, this.form)
      : this.productService.create(this.form);

    request$.subscribe({
      next: () => {
        this.saving = false;
        this.cancelEdit();
        this.loadProducts();
      },
      error: (err) => {
        this.saving = false;
        this.errorMessage = err.error?.error || 'Could not save product.';
      },
    });
  }

  deleteProduct(product: Product): void {
    if (!confirm(`Delete "${product.name}"? This will hide it from the storefront.`)) return;
    this.productService.delete(product.id).subscribe({
      next: () => this.loadProducts(),
      error: () => (this.errorMessage = 'Could not delete product.'),
    });
  }
}
