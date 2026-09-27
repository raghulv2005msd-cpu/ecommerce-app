import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Product } from '../../models/product.model';
import { ProductService } from '../../core/services/product.service';
import { CartService } from '../../core/services/cart.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  loading = true;
  errorMessage = '';
  searchTerm = '';
  addingId: number | null = null;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getAll(this.searchTerm ? { search: this.searchTerm } : undefined).subscribe({
      next: (products) => {
        this.products = products;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Could not load products. Is the backend running?';
        this.loading = false;
      },
    });
  }

  onSearch(): void {
    this.loadProducts();
  }

  addToCart(product: Product): void {
    if (!this.authService.isLoggedIn()) return;
    this.addingId = product.id;
    this.cartService.addOrUpdate({ productId: product.id, quantity: 1 }).subscribe({
      next: () => (this.addingId = null),
      error: () => (this.addingId = null),
    });
  }
}
